package hr.fer.zemris.java.tecaj.hw5.db.commands;


/**
 * Interface enables the implementing class to act as a command for
 * database queries.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface ICommand {

	/**
	 * Executes the command with the given query.
	 * 
	 * @param query query ba which to search the database
	 * @return returns the found object or objects
	 */
	public Object execute(String query);
	
	
	/**
	 * Gets the keyword with which to recognize the command.
	 * 
	 * @return returns the keyword of the command
	 */
	public String getKeyword();
}
