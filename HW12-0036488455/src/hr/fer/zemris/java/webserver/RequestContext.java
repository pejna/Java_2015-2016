package hr.fer.zemris.java.webserver;

import java.io.IOException;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Context of an internet request.
 * <p>
 * Output given in the constructor should be {@link Socket}.getOutputStream() to
 * work as intended. Saves cookies and manages the output to the {@link Socket}.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see SmartHttpServer
 * @see Socket
 */
public class RequestContext {

	/**
	 * Cookie container used for marking web pages. Holds name, value, domain
	 * and path of the website.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	public static class RCCookie {

		/**
		 * Name of the context.
		 */
		private String name;

		/**
		 * Value of tha name.
		 */
		private String value;

		/**
		 * Domain of the context.
		 */
		private String domain;

		/**
		 * Path of the context
		 */
		private String path;

		/**
		 * Max age of the context.
		 */
		private Integer maxAge;

		/**
		 * Is the cookie http only.
		 */
		private boolean httpOnly;

		/**
		 * Tag of the domain data of the cookie.
		 */
		private static final String DOMAIN_TAG = "Domain";

		/**
		 * Tag of the path data of the cookie.
		 */
		private static final String PATH_TAG = "Path";

		/**
		 * Tag of the max age data of the cookie.
		 */
		private static final String MAX_AGE_TAG = "Max-Age";


		/**
		 * Creates the cookie container.
		 * 
		 * @param name
		 *            name of the cookie
		 * @param value
		 *            value of the cookie
		 * @param maxAge
		 *            max age for the cookie
		 * @param domain
		 *            domain of the cookie
		 * @param path
		 *            path of the cookie
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path) {
			Objects.requireNonNull(name);
			Objects.requireNonNull(value);
			if (maxAge != null && maxAge < 0) {
				throw new IllegalArgumentException(
						"Warning - Cannot have age less than 0!");
			}

			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}


		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}


		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}


		/**
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}


		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}


		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}


		/**
		 * @return the httpOnly
		 */
		public boolean isHttpOnly() {
			return httpOnly;
		}


		/**
		 * @param httpOnly
		 *            the httpOnly to set
		 */
		public void setHttpOnly(boolean httpOnly) {
			this.httpOnly = httpOnly;
		}

	}

	/**
	 * Output stream of the context.
	 */
	private OutputStream outputStream;

	/**
	 * Charset of the context.
	 */
	private Charset charset;

	/**
	 * Encoding of the context.
	 */
	private String encoding = "UTF-8";

	/**
	 * Status code of the context.
	 */
	private int statusCode = 200;

	/**
	 * Status text of the context.
	 */
	private String statusText = "OK";

	/**
	 * Mime type of the context.
	 */
	private String mimeType = "text/html";

	/**
	 * Map of the parameters of the context.
	 */
	private Map<String, String> parameters;

	/**
	 * Map of the temporary parameters of the context.
	 */
	private Map<String, String> temporaryParameters;

	/**
	 * Map of the persistent parameters of the context.
	 */
	private Map<String, String> persistentParameters;

	/**
	 * Cookies saved by the context.
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Is the header generated.
	 */
	private boolean headerGenerated;


	/**
	 * Creates the context with the given parameters. Stream must not be null.
	 * Parameters, persistentParameters and outputCookies can be null. If null
	 * they are considered empty.
	 * 
	 * @param stream
	 *            stream to write to
	 * @param parameters
	 *            paramters to keep, can be null
	 * @param persistentParameters
	 *            persistent parameters to keep, can be null
	 * @param outputCookies
	 *            cookies to save, can be null
	 */
	public RequestContext(OutputStream stream, Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		Objects.requireNonNull(stream);

		this.outputStream = stream;

		if (parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}

		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}

		if (outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}

		this.temporaryParameters = new HashMap<>();
	}


	/**
	 * Returs the requested parameter. Null if not found.
	 * 
	 * @param name
	 *            name of the requested parameter
	 * @return returns the requested parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}


	/**
	 * @return returns the parameter name set
	 */
	public Set<String> getParameterNames() {
		return new HashSet<>(parameters.keySet());
	}


	/**
	 * Returs the requested parameter. Null if not found.
	 * 
	 * @param name
	 *            name of the requested parameter
	 * @return returns the requested parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}


	/**
	 * @return returns the parameter name set
	 */
	public Set<String> getPersistentParameterNames() {
		return new HashSet<>(persistentParameters.keySet());
	}


	/**
	 * Takes in a parameter with the given name.
	 * 
	 * @param name
	 *            name of the parameter
	 * @param value
	 *            value of the parameters
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}


	/**
	 * Removes a parameter with the given name.
	 * 
	 * @param name
	 *            name of the parameter
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}


	/**
	 * Returs the requested parameter. Null if not found.
	 * 
	 * @param name
	 *            name of the requested parameter
	 * @return returns the requested parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}


	/**
	 * @return returns the parameter name set
	 */
	public Set<String> getTemporaryParameterNames() {
		return new HashSet<>(temporaryParameters.keySet());
	}


	/**
	 * Takes in a parameter with the given name.
	 * 
	 * @param name
	 *            name of the parameter
	 * @param value
	 *            value of the parameters
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}


	/**
	 * Removes a parameter with the given name.
	 * 
	 * @param name
	 *            name of the parameter
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}


	/**
	 * Writes the given byte array to the output of the context. Also writes out
	 * the header if it already hasn't been written.
	 * 
	 * @param data
	 *            data to be written
	 * @return returns this context
	 * @throws IOException
	 *             throw if unable to write to output
	 */
	public RequestContext write(byte[] data) throws IOException {
		Objects.requireNonNull(data);
		generateHeader();
		outputStream.write(data);
		return this;
	}


	/**
	 * Writes the given string in charset to the output of the context. Also
	 * writes out the header if it already hasn't been written.
	 * 
	 * @param text
	 *            text to be written
	 * @return returns this context
	 * @throws IOException
	 *             throw if unable to write to output
	 */
	public RequestContext write(String text) throws IOException {
		Objects.requireNonNull(text);
		generateHeader();
		outputStream.write(text.getBytes(charset));
		return this;
	}


	/**
	 * Genereates the header.
	 * 
	 * @throws IOException
	 *             thrown if unable to write to output
	 */
	private void generateHeader() throws IOException {
		if (headerGenerated) {
			return;
		}
		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText)
				.append("\r\n");
		sb.append("Content-Type: ").append(mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=").append(encoding);
		}
		sb.append("\r\n");
		for (RCCookie c : outputCookies) {
			sb.append("Set-Cookie: ");
			sb.append(cookieToString(c));
			sb.append("\r\n");
		}
		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(charset));
		headerGenerated = true;
	}


	/**
	 * Transforms the cookie contents to string.
	 * 
	 * @param c
	 *            cookie to be transformed
	 * @return returns the string from cookie
	 */
	private String cookieToString(RCCookie c) {
		StringBuilder sb = new StringBuilder();
		sb.append(c.getName()).append("=").append("\"" + c.getValue() + "\"");
		if (c.getDomain() != null) {
			sb.append("; " + RCCookie.DOMAIN_TAG + "=").append(c.getDomain());
		}

		if (c.getPath() != null) {
			sb.append("; " + RCCookie.PATH_TAG + "=").append(c.getPath());
		}

		if (c.getMaxAge() != null) {
			sb.append("; " + RCCookie.MAX_AGE_TAG + "=").append(c.maxAge);
		}
		if (c.isHttpOnly()) {
			sb.append("; HttpOnly");
		}

		return sb.toString();
	}


	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		checkHeaderGenerated();
		this.encoding = encoding;
	}


	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderGenerated();
		this.statusCode = statusCode;
	}


	/**
	 * @param statusText
	 *            the statusText to set
	 */
	public void setStatusText(String statusText) {
		checkHeaderGenerated();
		this.statusText = statusText;
	}


	/**
	 * @param mimeType
	 *            the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		checkHeaderGenerated();
		this.mimeType = mimeType;
	}


	/**
	 * Adds a cookie to the context.
	 * 
	 * @param cookie
	 *            cookie to be added
	 */
	public void addRCCookie(RCCookie cookie) {
		Objects.requireNonNull(cookie);
		outputCookies.add(cookie);
	}


	private void checkHeaderGenerated() {
		if (headerGenerated) {
			throw new RuntimeException(
					"Warning - RequestContext: Header Already created!");
		}
	}
}
