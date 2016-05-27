package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Interface requires the implementing classes to provide
 * a name, description and an execution method. Designed to be used
 * with {@link Environment} implementing classes.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface ShellCommand {
	
	
	/**
	 * Executes the command according to the class documentation.
	 * 
	 * @param env environment in which to execute the command
	 * @param arguments arguments for the command
	 * @return returns ShellStatus.CONTINUE if the 
	 * {@link Environment} action is to be continued, or
	 * ShellStatus.TERMINATE if action is to be ended
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	
	/**
	 * Procures the name of the command.
	 * 
	 * @return returns the name of the command
	 */
	String getCommandName();
	
	
	/**
	 * Procures the command description. Each string in the list
	 * is a new line to be printed to the environment.
	 * 
	 * @return returns the command description
	 */
	List<String> getCommandDescription();
}
