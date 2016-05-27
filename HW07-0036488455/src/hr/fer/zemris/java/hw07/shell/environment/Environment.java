package hr.fer.zemris.java.hw07.shell.environment;

import java.io.IOException;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;


/**
 * Interface requires all implementing classes to act as environments for shell
 * classes. To enable writing and reading lommands and to be able to show
 * supported commands. Implementing classes should have in their documentation
 * what input and output sources they use.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface Environment {
	
	
	/**
	 * Reads a line from designated input.
	 * 
	 * @return returns the read line
	 * @throws IOException thrown if designated input is closed
	 */
	String readLine() throws IOException;
	
	
	/**
	 * Writes to the designated output.
	 * 
	 * @param text text to be written
	 * @throws IOException thrown if designated output is closed
	 */
	void write(String text) throws IOException;
	
	
	/**
	 * Writes a line to the designated output finishing with newline character.
	 * 
	 * @param text text to be written
	 * @throws IOException thrown if designated output is closed
	 */
	void writeln(String text) throws IOException;
	
	
	/**
	 * Enables iteration through supported commands.
	 * 
	 * @return returns an iterable list of supported commands
	 */
	Iterable<ShellCommand> commands();
	
	
	/**
	 * Gets the symbol that represents continuing taking input for the previous
	 * command line.
	 * 
	 * @return returns the multiline symbol
	 */
	Character getMultilineSymbol();
	
	
	/**
	 * Sets the multiline symbol.
	 * 
	 * @param symbol symbol to be set as multiline
	 */
	void setMultilineSymbol(Character symbol);
	
	
	/**
	 * Gets the symbol that shows the availability of input to the user.
	 * 
	 * @return returns the prompt symbol
	 */
	Character getPromptSymbol();
	
	
	/**
	 * Sets the prompt symbol.
	 * 
	 * @param symbol symbol to be set as prompt
	 */
	void setPromptSymbol(Character symbol);
	
	
	/**
	 * Gets the symbol that is used by the used to aks for more lines for input.
	 * 
	 * @return returns the morelines symbol
	 */
	Character getMoreLinesSymbol();
	
	
	/**
	 * Sets the morelines symbol.
	 *
	 * @param symbol symbol to be set as morelines
	 */
	void setMoreLinesSymbol(Character symbol);
}
