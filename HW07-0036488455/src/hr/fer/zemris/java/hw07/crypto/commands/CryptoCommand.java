package hr.fer.zemris.java.hw07.crypto.commands;


/**
 * Interface to be used by the classes used as commands for 
 * {@link Crypto} class. Each class should describe it's parameters
 * for execute method and should provide it's distinct keyword.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface CryptoCommand {

	
	/**
	 * Gets the keyword by which the command can be distinguished.
	 * @return returns the keyword
	 */
	public String getKeyword();
	
	
	/**
	 * Executes the action of this command.
	 * 
	 * @param args parameters used by the command, should be 
	 * arraged as stated in the class description
	 */
	public void execute(String[] args);
}
