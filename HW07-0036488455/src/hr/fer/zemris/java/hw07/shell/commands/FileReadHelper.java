package hr.fer.zemris.java.hw07.shell.commands;


/**
 * Helper class for splitting strings into file paths.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class FileReadHelper {

	/**
	 * Position of input path.
	 */
	private static final int FIRST_POSITION = 0;

	/**
	 * Position of output path.
	 */
	private static final int SECOND_POSITION = 1;

	/**
	 * Number of paths expected.
	 */
	private static final int EXPECTED_ARGUMENTS = 2;

	/**
	 * Character used as quotation marks.
	 */
	private static final char QUOTES = '"';
	
	
	/**
	 * Splits the given string into 2 path names. If a path
	 * has whitespaces in it, it should be in quotation marks.
	 * 
	 * @param arguments string that contains paths
	 * @return return returns array with either 1 or 2 found
	 * paths
	 */
	public static String[] divideIntoPaths(String arguments){
		boolean foundQuote = false;
		int i = 0;
		
		while(i < arguments.length()){
			
			if(arguments.charAt(i) == QUOTES){
				foundQuote = !foundQuote;
			}
			
			if(Character.isWhitespace(arguments.charAt(i))){
				if(!foundQuote){
					break;
				}
			}
			
			i++;
		}
		
		String[] array;
		
		if( i >= arguments.length()){
			array = new String[1];
			array[0] = arguments.substring(0, i);
			return array;
		}

		array = new String[EXPECTED_ARGUMENTS];
		array[FIRST_POSITION] = arguments.substring(0, i)
				.replace(QUOTES, ' ').trim();
		array[SECOND_POSITION] = arguments.substring(i + 1)
				.replace(QUOTES, ' ').trim();

		return array;
	}
	
	
}
