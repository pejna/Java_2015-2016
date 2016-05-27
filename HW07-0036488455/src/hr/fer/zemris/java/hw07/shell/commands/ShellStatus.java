package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.environment.Environment;

/**
 * Enumeration of the possible messages for the {@link Environment}
 * from the {@link ShellCommand} after finishing execution.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public enum ShellStatus {
	
	/**
	 * Message to continue the {@link Environment} execution.
	 */
	CONTINUE,
	
	/**
	 * Message to end the {@link Environment} execution.
	 */
	TERMINATE
}
