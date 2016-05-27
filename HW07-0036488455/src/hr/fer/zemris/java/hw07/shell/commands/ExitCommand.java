package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Exit command used with {@link Environment} implementing classes.
 * Exits the session of using the environment.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ExitCommand extends AbstractCommand {

	/**
	 * Name of the command.
	 */
	private static final String NAME = "exit";
	
	/**
	 * Lines of command description.
	 */
	private static final String[] DESCRIPTION = {
			" - exits the shell"
			};
	
	
	/**
	 * Creates the command.
	 */
	public ExitCommand() {
		super(NAME, DESCRIPTION);
	}

	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		return ShellStatus.TERMINATE;
	}
}
