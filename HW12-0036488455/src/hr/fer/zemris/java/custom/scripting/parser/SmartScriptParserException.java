package hr.fer.zemris.java.custom.scripting.parser;


/**
 * Exception thrown if an error in parser occures.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Serial number of this error.
	 */
	private static final long 
				serialVersionUID = -7146405271722626823L;

	/**
	 * Cureates an empty exception.
	 */
	public SmartScriptParserException(){
	}
	
	
	/**
	 * Creates an exception with the given string as a message.
	 * 
	 * @param message message that this exception carries
	 */
	public SmartScriptParserException(String message){
		super(message);
	}
	
}
