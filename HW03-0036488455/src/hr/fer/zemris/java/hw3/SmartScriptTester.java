package hr.fer.zemris.java.hw3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Tests the {@link SmartScriptParser} with reading a document form
 * address given by terminal.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class SmartScriptTester {
	
	
	/**
	 * Runs the program.
	 * 
	 * @param args address of the document
	 */
	public static void main(String[] args){
		if(args.length == 0){
			System.err.println("Not enough arguments! Aborting!");
			System.exit(-1);
		}
		
		String filepath = args[0];
		
		String docBody = "";
		try {
			docBody = new String(
					 Files.readAllBytes(Paths.get(filepath)),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e1) {
			System.err.println("Incorrect input! Aborting!");
			System.exit(-1);
		}
		SmartScriptParser parser = null;
		try {
		 parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		 System.out.println("Unable to parse document!");
		 System.exit(-1);
		} catch(Exception e) {
		 System.out.println("If this line ever executes, you have failed this class!");
		 System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		 // content of docBody
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String secondDocumentBody = createOriginalDocumentBody(document2);
		// now document and document2 should be structurally identical trees
		System.out.println(secondDocumentBody);
	}

	
	/**
	 * Writes the original document body from the given document node.
	 * 
	 * @param document document node from which to create string
	 * @return returns original document body
	 */
	private static String createOriginalDocumentBody(
			DocumentNode document) {
		return document.toString();
	}
}
