package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Charsets command to be used with {@link Environment} implementing
 * classes. Takes no arguments and prints supported charsets to the
 * environment, one charset per line.
 * @author Peda
 *
 */
public class CharsetsCommand extends AbstractCommand {

	/**
	 * Name of the command.
	 */
	private static final String NAME = "charsets";
	
	/**
	 * Description of the command.
	 */
	private static final String[] DESCRIPTION = {
		" - gives the user a list of supported charsets"
	};
	
	
	/**
	 * Creates the command.
	 */
	public CharsetsCommand() {
		super(NAME, DESCRIPTION);
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);
		
		Map<String, Charset> map = Charset.availableCharsets();
		
		try {
			env.writeln("Supported charsets are: ");
		} catch (IOException ignorable) {}
		
		for(String string : map.keySet()){
			try {
				env.writeln(string);
			} catch (IOException ignorable) {}
		}
		
		return ShellStatus.CONTINUE;
	}
}
