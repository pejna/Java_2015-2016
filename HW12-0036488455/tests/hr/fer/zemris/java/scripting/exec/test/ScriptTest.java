package hr.fer.zemris.java.scripting.exec.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Tests the {@link SmartScriptEngine} with 4 different tests.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see SmartScriptEngine
 */
public class ScriptTest {

	/**
	 * Executes the test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Test1();
		Test2();
		Test3();
		Test4();
	}


	/**
	 * Test No.1.
	 */
	private static void Test1() {
		String documentBody = readFromDisk("osnovni.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters,
						cookies)).execute();
	}


	/**
	 * Test No.2.
	 */
	private static void Test2() {
		String documentBody = readFromDisk("zbrajanje.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters,
						cookies)).execute();
	}


	/**
	 * Test No.3.
	 */
	private static void Test3() {
		String documentBody = readFromDisk("brojPoziva.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters,
				persistentParameters, cookies);

		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), rc)
						.execute();
		System.out.println("Vrijednost u mapi: "
				+ rc.getPersistentParameter("brojPoziva"));
	}


	/**
	 * Test No.4.
	 */
	private static void Test4() {
		String documentBody = readFromDisk("fibonacci.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters,
						cookies)).execute();

	}


	/**
	 * Reads a string containing all contents of a file from the path specified
	 * by the given string.
	 * 
	 * @param string
	 *            path of the file
	 * @return returns the string containing all contents
	 */
	private static String readFromDisk(String string) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(string));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded);
	}
}
