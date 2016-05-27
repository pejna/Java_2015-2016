package hr.fer.zemris.java.tecaj.hw5.db.commands;


/**
 * Command used to exit the application printing an appropriate 
 * message.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ExitCommand implements ICommand {

	/**
	 * Represents this command.
	 */
	private static final String keyword = "exit";
	
	/**
	 * Message to be printed when executing.
	 */
	private static final String CLOSING_MESSAGE = "Bye bye!";
	
	@Override
	public Object execute(String query) {
		System.out.println(CLOSING_MESSAGE);
		System.exit(0);
		
		//required by the method, not used
		return null;
	}

	@Override
	public String getKeyword() {
		return keyword;
	}

}
