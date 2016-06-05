package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Server program that serves requests made to an ip address and port given in
 * the configuration files. Can serve SmartScript requests.
 * <p>
 * An example of a config file is this. Keep in mind, all parameter names must
 * stay the same.
 * 
 * # On which address server listens? server.address = 127.0.0.1
 *
 * # On which port server listens? server.port = 5721
 *
 * # How many threads should we use for thread pool? server.workerThreads = 10
 *
 * # What is the path to root directory from which we serve files?
 * server.documentRoot = webroot
 *
 * # What is the path to configuration file for extension to mime-type mappings?
 * server.mimeConfig = config/mime.properties
 *
 * # What is the duration of user sessions in seconds? As configured, it is 10
 * minutes. session.timeout = 600
 *
 * # What is the path to configuration file for url to worker mappings?
 * server.workers = config/workers.properties
 * 
 * Worker config and mime config contains full classpaths for the desired wokers
 * tied to the desired keyword names starting with /.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see IWebWorker
 * @see RequestContext
 * @see SmartScriptParser
 * @see SmartScriptEngine
 */
public class SmartHttpServer {

	/**
	 * Ip address of the server.
	 */
	private String address;

	/**
	 * Port of the server.
	 */
	private int port;

	/**
	 * Number of worker threads.
	 */
	private int workerThreads;

	/**
	 * Time for the cookies to timeout.
	 */
	private int sessionTimeout;

	/**
	 * Map of mime keywords and their mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * Main serveThread
	 */
	private ServerThread serverThread;

	/**
	 * Pool of worker threads.
	 */
	private ExecutorService threadPool;

	/**
	 * Root for the documents.
	 */
	private Path documentRoot;

	/**
	 * MAp of web workers.
	 */
	private Map<String, IWebWorker> workersMap;

	/**
	 * Map of saved sessions.
	 */
	private Map<String, SessionMapEntry> sessions;

	/**
	 * Random number generator.
	 */
	private Random sessionRandom;

	/**
	 * Key of workthread number property.
	 */
	private static final String PROPERTY_WORKTHREADS = "server.workerThreads";

	/**
	 * Key of cookie timeout property.
	 */
	private static final String PROPERTY_TIMEOUT = "session.timeout";

	/**
	 * Key of document root property.
	 */
	private static final String PROPERTY_ROOT = "server.documentRoot";

	/**
	 * Key of address property.
	 */
	private static final String PROPERTY_ADDRESS = "server.address";

	/**
	 * Key of port property.
	 */
	private static final String PROPERTY_PORT = "server.port";

	/**
	 * Key of mime config property.
	 */
	private static final String PROPERTY_MIMES = "server.mimeConfig";

	/**
	 * Key of worker config property.
	 */
	private static final String PROPERTY_WORKERS = "server.workers";

	/**
	 * Extension of SmartScript files.
	 */
	private static final String SCRIPT_EXTENSION = "smscr";

	/**
	 * Package of worker classes.
	 */
	private static final String WORKER_PACKAGE = "hr.fer.zemris."
			+ "java.webserver.workers";

	/**
	 * Length of session id cookie property.
	 */
	private static final int SID_LENGTH = 20;

	/**
	 * Name of session id cookie property.
	 */
	private static final String SID_COOKIE_NAME = "sid";

	/**
	 * Period of session id cookie expiry in miliseconds.
	 */
	protected static final long SID_CLEAN_PERIOD = 300 * 1000;


	/**
	 * Creates the server from the config file.
	 * 
	 * @param configFileName
	 *            path to the config file
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		Properties mimeProperties = new Properties();
		Properties workerProperties = new Properties();
		try {
			properties.load(Files.newInputStream(Paths.get(configFileName),
					StandardOpenOption.READ));
		} catch (IOException e) {
			System.err.println("Aborting - Couldn't read properties!");
			System.exit(-1);
		}
		try {
			mimeProperties.load(Files.newInputStream(
					Paths.get(properties.getProperty(PROPERTY_MIMES)),
					StandardOpenOption.READ));
		} catch (IOException e) {
			System.err.println("Aborting - Couldn't read mime properties!");
			System.exit(-1);

		}
		try {
			workerProperties.load(Files.newInputStream(
					Paths.get(properties.getProperty(PROPERTY_WORKERS)),
					StandardOpenOption.READ));
		} catch (IOException e) {
			System.err.println("Aborting - Couldn't read worker properties!");
			System.exit(-1);
		}

		workerThreads = Integer
				.parseInt(properties.getProperty(PROPERTY_WORKTHREADS));
		sessionTimeout = Integer
				.parseInt(properties.getProperty(PROPERTY_TIMEOUT));
		documentRoot = Paths.get(properties.getProperty(PROPERTY_ROOT))
				.normalize();
		address = properties.getProperty(PROPERTY_ADDRESS);
		port = Integer.parseInt(properties.getProperty(PROPERTY_PORT));

		for (Object key : mimeProperties.keySet()) {
			mimeTypes.put((String) key,
					mimeProperties.getProperty((String) key));
		}

		workersMap = new HashMap<>();
		for (Object key : workerProperties.keySet()) {
			String className = workerProperties.getProperty((String) key);
			try {
				IWebWorker worker = (IWebWorker) this.getClass()
						.getClassLoader().loadClass(className).newInstance();
				workersMap.put((String) key, worker);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				continue;
			}
		}

		sessions = new ConcurrentHashMap<>();
		sessionRandom = new Random();
	}


	/**
	 * Starts the server.
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);

		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.run();
		}
		sidCleaner.run();
	}


	/**
	 * Stops the server.
	 */
	@SuppressWarnings("deprecation")
	protected synchronized void stop() {
		serverThread.stop();
		threadPool.shutdown();
	}

	/**
	 * Main thread of the server, constantly recieves data and gives the work to
	 * the {@link ClientWorker}s.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {

		@SuppressWarnings("resource")
		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				serverSocket.setSoTimeout(sessionTimeout);

				Socket client = null;
				ClientWorker cw;
				while (true) {
					try {
						client = serverSocket.accept();
					} catch (Exception ignorable) {
						continue;
					}
					cw = new ClientWorker(client);
					threadPool.submit(cw);
				}

			} catch (IOException e) {
				System.err.println(
						"Aborting - Port already taken!" + e.getMessage());
				System.exit(-1);
			}
		}
	}

	/**
	 * Entry for the session map. Container for permanent parameters found in
	 * requests, session ids and it's timeout time.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private static class SessionMapEntry {

		/**
		 * Session id.
		 */
		String sid;

		/**
		 * Time until timeout.
		 */
		long validUntil;

		/**
		 * MAp of permanent parameters.
		 */
		Map<String, String> map;
	}

	/**
	 * Main client worker. Serves the request on the socket provided to it by
	 * the {@link ServerThread}.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable {

		/**
		 * Socket with the request.
		 */
		private Socket csocket;

		/**
		 * Input stream from the socket.
		 */
		private PushbackInputStream istream;

		/**
		 * Output stream to the socket.
		 */
		private OutputStream ostream;

		/**
		 * Version of the request.
		 */
		private String version;

		/**
		 * Method of the request.
		 */
		private String method;

		/**
		 * Parameters of the request.
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * Permanent parameters of the request.
		 */
		private Map<String, String> permParams = null;

		/**
		 * Cookies to output to the socket.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RCCookie>();

		/**
		 * Session id.
		 */
		private String sid;

		/**
		 * Default mime type.
		 */
		private static final String DEFAULT_MIME = "application/octet-stream";


		/**
		 * Creates the worker with connection to the given socket.
		 * 
		 * @param csocket
		 *            socket with request
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}


		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				List<String> request = readRequest();

				if (request.size() < 1) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String firstLine = request.get(0);

				String[] firstLineArgs = firstLine.split(" ");

				if (firstLineArgs.length != 3) {
					System.err.println("bad request");
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLineArgs[0].toUpperCase();
				if (!method.equals("GET")) {
					System.err.println("error method");
					sendError(ostream, 400, "Method Not Allowed");
					return;
				}

				version = firstLineArgs[2].toUpperCase();
				if (!version.equals("HTTP/1.1")
						&& !version.equals("HTTP/1.0")) {
					System.err.println("error " + version);
					sendError(ostream, 400, "HTTP Version Not Supported");
					return;
				}

				String sidCandidate = findSidCookie(request);
				if (sidCandidate == null) {
					registerSession();
				} else {
					SessionMapEntry entry = sessions.get(sidCandidate);
					if (entry == null) {
						registerSession();
					} else if (entry.validUntil < new Date().getTime()) {
						registerSession();
					} else {
						sid = entry.sid;
						entry.validUntil = new Date().getTime()
								+ sessionTimeout * 1000;
						this.permParams = entry.map;
					}
				}

				String[] requestedPathArray = firstLineArgs[1].split("\\?");
				String path = requestedPathArray[0];

				if (requestedPathArray.length > 1) {
					String paramString = requestedPathArray[1];
					parseParameters(paramString);
				}

				RequestContext rc = new RequestContext(ostream, params,
						permParams, outputCookies);

				// convention approach
				if (path.startsWith("/ext/")) {
					path = path.substring(5);
					path = WORKER_PACKAGE + "." + path;
					((IWebWorker) this.getClass().getClassLoader()
							.loadClass(path).newInstance()).processRequest(rc);
					return;
				}

				// configuration approach
				if (workersMap.containsKey(path)) {
					workersMap.get(path).processRequest(rc);
					return;
				}

				path = path.substring(1);
				Path requestedPath = documentRoot.normalize().resolve(path);
				if (!requestedPath.toFile().exists()) {
					System.out.println("not exist");
					sendError(ostream, 403, "Forbidden acces");
					return;
				}

				if (!requestedPath.toFile().isFile()
						|| !requestedPath.toFile().canRead()) {
					System.out.println("not found");
					sendError(ostream, 404, "File not found");
					return;
				}

				String[] nameFragments = requestedPath.toFile().getName()
						.split("\\.");
				String mime = mimeTypes
						.get(nameFragments[nameFragments.length - 1].trim());

				if (mime == null) {
					mime = DEFAULT_MIME;
				}

				rc.setMimeType(mime);
				rc.setStatusCode(200);

				if (nameFragments[1].equals(SCRIPT_EXTENSION)) {
					executeScript(requestedPath, rc);
				} else {
					byte[] readFile = readFromFile(requestedPath);
					rc.write(readFile);
				}

			} catch (IOException e1) {
				System.err.println("Problem with input/output!");
				return;
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException ignorable) {
				sendError(ostream, 403, "Forbidden acces");
			}

			try {
				csocket.close();
			} catch (IOException ignorable) {
			}
		}


		/**
		 * Registers a session to the session map.
		 */
		private void registerSession() {
			sid = generateSid();
			SessionMapEntry entry = new SessionMapEntry();
			entry.sid = sid;
			entry.map = new HashMap<>();
			permParams = new HashMap<>();
			entry.validUntil = new Date().getTime() + sessionTimeout * 1000;
			entry.map = permParams;
			sessions.put(sid, entry);
			RCCookie cookie = new RequestContext.RCCookie("sid", sid, null,
					address, "/");
			cookie.setHttpOnly(true);
			outputCookies.add(cookie);

		}


		/**
		 * Executes a script in the given path.
		 * 
		 * @param requestedPath
		 *            path of the script
		 * @param rc
		 *            context of the request
		 */
		private void executeScript(Path requestedPath, RequestContext rc) {
			String script = new String(readFromFile(requestedPath));
			SmartScriptParser parser = new SmartScriptParser(script);
			SmartScriptEngine engine = new SmartScriptEngine(
					parser.getDocumentNode(), rc);
			engine.execute();
		}


		/**
		 * Sends an error message to the socket.
		 * 
		 * @param stream
		 *            socket output stream
		 * @param code
		 *            error message code
		 * @param message
		 *            message to send
		 */
		private void sendError(OutputStream stream, int code, String message) {
			try {
				stream.write(("HTTP/1.1 " + code + " " + message + "\r\n"
						+ "Server: Peda Java server\r\n"
						+ "Content-Type: text/plain;charset=UTF-8\r\n"
						+ "Content-Length: 0\r\n" + "Connection: close\r\n"
						+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			} catch (IOException ignorable) {
			}
		}


		/**
		 * Reads an entire file in the given path.
		 * 
		 * @param path
		 *            path to the file
		 * @return returns contents of the file
		 */
		private byte[] readFromFile(Path path) {

			try {
				return Files.readAllBytes(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}


		/**
		 * Parses parameters from the given string and places them in the params
		 * map.
		 * 
		 * @param paramString
		 *            string containing parameters
		 */
		private void parseParameters(String paramString) {
			String[] pairs = paramString.split("\\&");
			for (String pair : pairs) {
				String[] singles = pair.split("\\=");
				params.put(singles[0].trim(), singles[1].trim());
			}
		}


		/**
		 * Reads the request and forms it into a list of request strings for
		 * easier managment.
		 * 
		 * @return returns the request string list
		 */
		private List<String> readRequest() {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			int b;
			l: try {
				while ((b = istream.read()) > 0) {
					if (b != 13) {
						bos.write(b);
					}
					switch (state) {
					case 0:
						if (b == 13) {
							state = 1;
						} else if (b == 10) {
							state = 4;
						}
						break;
					case 1:
						if (b == 10) {
							state = 2;
						} else {
							state = 0;
						}
						break;
					case 2:
						if (b == 13) {
							state = 3;
						} else {
							state = 0;
						}
						break;
					case 3:
						if (b == 10) {
							break l;
						} else {
							state = 0;
						}
						break;
					case 4:
						if (b == 10) {
							break l;
						} else {
							state = 0;
						}
						break;
					}
				}
			} catch (IOException e) {
				System.err.println("Aborting - Couldn't read from input!");
			}

			String requestHeader = new String(bos.toByteArray(),
					StandardCharsets.US_ASCII);
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}


		/**
		 * Finds a session id cookie from the given request list.
		 * 
		 * @param request
		 *            list with request lines
		 * @return returns the session id, or null if not found
		 */
		private String findSidCookie(List<String> request) {
			for (String s : request) {
				if (s.startsWith("Cookie:")) {
					String cookies = s.substring("Cookie:".length()).trim();
					String[] cookieArray = cookies.split(";");
					for (String cookie : cookieArray) {
						String[] members = cookie.split("\\=");
						if (members[0].toUpperCase()
								.equals(SID_COOKIE_NAME.toUpperCase())) {
							return members[1].replace('"', ' ').trim();
						}
					}
					return null;
				}
			}
			return null;
		}
	}

	/**
	 * Cleaner for the session ids that timed out.
	 */
	private Runnable sidCleaner = new Runnable() {

		@Override
		public void run() {
			while (true) {
				try {
					this.wait(SID_CLEAN_PERIOD);
				} catch (InterruptedException ignorable) {
				}
				List<Object> toRemove = new ArrayList<>();
				for (Object key : sessions.keySet()) {
					if (sessions.get(key).validUntil > new Date().getTime()) {
						toRemove.add(key);
					}
				}
				toRemove.forEach((e) -> {
					sessions.remove(e);
				});
			}

		}
	};


	/**
	 * Generates a random session id.
	 * 
	 * @return returns the session id
	 */
	private String generateSid() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SID_LENGTH; i++) {
			sb.append('A' + (sessionRandom.nextInt() * 100) % 26);
		}

		return sb.toString();
	}


	/**
	 * Executes the program.
	 * 
	 * @param args
	 *            1 member, path to the config files
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Aborting - No config path given!");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}
}
