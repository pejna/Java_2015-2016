package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Help command used with {@link Environment} implementing classes.
 * Prints out all supported commands or prints out the command
 * whose name was given as argument.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class HelpCommand extends AbstractCommand {

	/**
	 * Name of the command.
	 */
	private static final String NAME = "help";
	
	/**
	 * Lines of command description.
	 */
	private static final String[] DESCRIPTION = {
			" - lists out all commands",
			" - if given command name prints it's description"
			};
	
	
	/**
	 * Creates the command.
	 */
	public HelpCommand() {
		super(NAME, DESCRIPTION);
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);
		
		if(arguments == null){
			try {
				env.writeln("Supported commands are: ");
			
				for(ShellCommand command : env.commands()){
					env.writeln(command.getCommandName());
				}
			} catch (IOException ignorable) {}

		} else {
			arguments = arguments.trim();
			
			
			boolean found = false;
			ShellCommand requested = null;
			
			for(ShellCommand command : env.commands()){
				if(command.getCommandName().toUpperCase()
						.equals(arguments.toUpperCase())){
					requested = command;
					found = true;
					break;
				}
			}
			
			if(!found){
				try {
					env.writeln("No such command found,");
					env.writeln("please use HELP for listing "
							+ "of supported commands!");
				} catch (IOException ignorable) {}
			} else {
				for(String string : requested
						.getCommandDescription()){
					try {
						env.writeln(string);
					} catch (IOException e) {}
				}
			}
			
		}
		
		return ShellStatus.CONTINUE;
	}
}
