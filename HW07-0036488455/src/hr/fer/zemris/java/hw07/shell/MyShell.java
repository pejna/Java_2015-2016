package hr.fer.zemris.java.hw07.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeCommand;
import hr.fer.zemris.java.hw07.shell.environment.Environment;
import hr.fer.zemris.java.hw07.shell.environment.ShellEnvironment;

/**
 * Program immitates shell programs found in windows and linux operating
 * systems. Provides basic file managment and listing capabilities. To list all
 * supported commands type HELP. For more detailed informations on particular
 * command type HELP followed by that command's name.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class MyShell {

	/**
	 * Map containing all supported commands.
	 */
	private static Map<String, ShellCommand> commands;

	/**
	 * Environment it which the shell operates.
	 */
	private static Environment environment;

	
	/**
	 * Initializes all commands and the environment.
	 */
	static {
		commands = new HashMap<>();
		
		ShellCommand[] commandArray = { new CatCommand(),
				new CharsetsCommand(), new CopyCommand(),
				new ExitCommand(), new HelpCommand(),
				new HexdumpCommand(), new LsCommand(),
				new MkdirCommand(), new SymbolCommand(),
				new TreeCommand() };

		for (ShellCommand command : commandArray) {
			commands.put(command.getCommandName().toUpperCase(),
					command);
		}

		environment = new ShellEnvironment(commands.values());
	}

	
	/**
	 * Executes the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		try {

			
			environment.writeln("Welcome to PeđaShell v1.0");
			
			ShellStatus status = ShellStatus.CONTINUE;

			while (status == ShellStatus.CONTINUE) {
				String input = getInput();

				String commandName = commandFromString(input);
				String arguments = argumentsFromString(input);

				ShellCommand command = commands.get(commandName);

				if (command == null) {
					environment.writeln("Unrecognized command! "
							+ "Please use HELP for listing "
							+ "of recognized commands");
					continue;
				}
				
				status = command.executeCommand(
						environment, arguments);
			}
			
			environment.writeln("Exiting! Bye bye!");
			
		} catch (IOException exception) {
			System.err.println("Aborting - Input/output error!");
			System.exit(-1);
		}
	}

	
	/**
	 * Gets the user input through multiple lines if needed and creates
	 * a string from it.
	 * 
	 * @return returns the entire user input
	 * @throws IOException thrown if input stream is closed
	 */
	private static String getInput() throws IOException {
		String input;
		boolean done = false;
		int linesRead = 0;

		StringBuilder sb = new StringBuilder();

		while (!done) {
			if (linesRead == 0) {
				environment.write(
						environment.getPromptSymbol().toString());
			} else {
				environment.write(
						environment.getMultilineSymbol().toString());
			}

			input = environment.readLine();

			if (input.length() == 0) {
				environment.writeln("You haven't entered anything!");
				continue;
			} else {
				linesRead++;
			}

			if (!environment.getMoreLinesSymbol()
					.equals(input.charAt(input.length() - 1))) {
				done = true;
			} else {
				input = input.substring(0, input.length()-1);
			}
			
			sb.append(input);
		}

		return sb.toString().trim();
	}

	
	/**
	 * Extracts the command name from the given string.
	 * 
	 * @param string
	 *            string to be dissected
	 * @return returns the command name as uppercase letters fit for map
	 *         searching
	 */
	private static String commandFromString(String string) {
		int counter = 0;

		while (counter < string.length()) {
			if (Character.isWhitespace(string.charAt(counter))) {
				break;
			}

			counter++;
		}

		return string.substring(0, counter).toUpperCase();
	}

	
	/**
	 * Extracts the command arguments from the given string.
	 * 
	 * @param string
	 *            string to be dissected
	 * @return returns a string containing the arguments, or null if none were
	 *         found
	 */
	private static String argumentsFromString(String string) {
		int counter = 0;

		while (counter < string.length()) {
			if (Character.isWhitespace(string.charAt(counter))) {
				break;
			}

			counter++;
		}

		if (counter == string.length()) {
			return null;
		}

		return string.substring(counter + 1).trim();
	}
}
