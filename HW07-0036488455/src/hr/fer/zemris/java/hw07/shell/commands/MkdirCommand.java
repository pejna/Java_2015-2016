package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Mkdir command to be used with {@link Environment} implementing
 * classes. Creates a folder at the given path.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class MkdirCommand extends AbstractCommand {

	/**
	 * Name of the command.
	 */
	private static final String NAME = "mkdir";
	
	/**
	 * Description of the command.
	 */
	private static final String[] DESCRIPTION = {
			" - creates a folder at the given path"
	};
	
	
	/**
	 * Creates the command.
	 */
	public MkdirCommand() {
		super(NAME, DESCRIPTION);
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);
		
		try { 
			if(arguments == null){
				env.writeln("No file given to create!");
				return ShellStatus.CONTINUE;
			}
			
			arguments = arguments.replace('"', ' ').trim();
			File file = Paths.get(arguments).toFile();
			
			if(file.exists()){
				env.writeln("A file at that "
						+ "location already exists!");
				return ShellStatus.CONTINUE;
			}
			
			if(file.mkdirs()){
				env.writeln("Couldn't create directory! Try again"
						+ "with different name!");
				return ShellStatus.CONTINUE;
			}
			
		} catch (IOException ignorable) {
			
		} catch (InvalidPathException e) {
			try {
				env.writeln("Couldn't create file with given path!");
			} catch (IOException e1) {}
		}
		
		return ShellStatus.CONTINUE;
	}
}
