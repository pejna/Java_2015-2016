package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;

/**
 * Cat command to be used with {@link Environment} implementing classes. Prints
 * out the contents of the file at provided path to the environment with default
 * or if given, given charset.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class CatCommand extends AbstractCommand {

	/**
	 * Name of the command.
	 */
	private static final String NAME = "cat";

	/**
	 * Description of the command.
	 */
	private static final String[] DESCRIPTION = {
			"- takes a path to a file and and optional charset name "
			+ "and prints out the contents of the file to the environment",
			"check supported charsets by using CHARSETS command" };

	/**
	 * Size of the block in the memory.
	 */
	private static final int BLOCK_SIZE = 4096;

	
	/**
	 * Creates the command.
	 */
	public CatCommand() {
		super(NAME, DESCRIPTION);
	}

	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);
		try {
			if (arguments == null) {
				env.writeln("No arguments given!");
				return ShellStatus.CONTINUE;
			}
			
			String[] args = FileReadHelper.divideIntoPaths(arguments);
			
			Charset charset;
			
			if(args.length == 1){
				charset = Charset.defaultCharset();
			} else {
				charset = Charset.forName(args[1]);
			}
			
			InputStream input = Files.newInputStream(Paths.get(args[0]),
					StandardOpenOption.READ);
			
			byte[] buffer = new byte[BLOCK_SIZE];
			int read = 0;
			
			while((read = input.read(buffer)) > 0){
				env.writeln(new String(buffer, 0, read, charset));
			}
			
			input.close();
			
		} catch (IOException e) {
			try {
				env.writeln("Given path does not exist!");
			} catch (IOException ignorable) {}
		} catch (Exception e1) {
			try {
				env.writeln("Couldn't deduce charset from arguments!");
			} catch (IOException ignorable) {}
		}

		return ShellStatus.CONTINUE;
	}
}
