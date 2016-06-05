package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Program demonstrates the usage of {@link RequestContext} class with output
 * being the files primjer1.txt, primjer2.txt, primjer3.txt.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see RequestContext
 */
public class DemoRequestContext {

	/**
	 * Starts the program
	 * 
	 * @param args
	 *            not used
	 * @throws IOException
	 *             thrown if an error occurss
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}


	/**
	 * Demonstrates the first test and writes it to given filepath.
	 * 
	 * @param filePath
	 *            filepath of the output file
	 * @param encoding
	 *            encdoging for the output
	 * @throws IOException
	 *             thrown if an error occurs
	 */
	private static void demo1(String filePath, String encoding)
			throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}


	/**
	 * Demonstrates the second test and writes it to given filepath.
	 * 
	 * @param filePath
	 *            filepath of the output file
	 * @param encoding
	 *            encdoging for the output
	 * @throws IOException
	 *             thrown if an error occurs
	 */
	private static void demo2(String filePath, String encoding)
			throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(
				new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
