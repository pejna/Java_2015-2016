package hr.fer.zemris.java.tecaj.hw3.prob2;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Tests does the parser parse a string.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class TestParser {

	
	/**
	 * Runs the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args){
		String text = "This is sample text. "
				+ "{$ FOR i 1 10 1 $} "
				+ "This is {$= i $}-th time this message is generated."
				+ "{$END$}"
				+ "{$FOR i 0 10 2 $}"
				+ "sin({$=i$}^2) = {$= i i * @sin 0.000 @decfmt $}"
				+ "{$END$}";
		String docBody = text;
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
		System.out.println("Done!");
	}
}
