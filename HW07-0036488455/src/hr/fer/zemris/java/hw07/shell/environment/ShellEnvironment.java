package hr.fer.zemris.java.hw07.shell.environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;


/**
 * Environment implementation meant to emulate shell environment
 * of windows or linux computers. Gets it's inputs from standard
 * input and writes out to standard output.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ShellEnvironment implements Environment {

	/**
	 * Commands supported by the {@link Environment}.
	 */
	private Collection<ShellCommand> commands;

	/**
	 * Reader used by the environment.
	 */
	private BufferedReader reader;

	/**
	 * Writer used by the environment.
	 */
	private BufferedWriter writer;
	
	/**
	 * Symbol to denote input spans multiple lines.
	 */
	private Character multilineSymbol;
	
	/**
	 * Symbol to request to use more lines for input.
	 */
	private Character morelinesSymbol;
	
	/**
	 * Symbol that signifies the awaiting of used input.
	 */
	private Character promptSymbol;
	
	/**
	 * Deafalt multiline symbol.
	 */
	private static final Character DEFAULT_MULTILINE_SYMBOL = '|';
	
	/**
	 * Default morelines symbol.
	 */
	private static final Character DEFAULT_MORELINES_SYMBOL = '\\';
	
	/**
	 * Default prompt symbol.
	 */
	private static final Character DEFAULT_PROMPT_SYMBOL = '>';

	
	/**
	 * Creates the {@link Environment} that supports given commands.
	 * 
	 * @param commands commands that the environment supports
	 */
	public ShellEnvironment(Collection<ShellCommand> commands) {
		Objects.requireNonNull(commands);
		this.commands = commands;

		reader = new BufferedReader(new InputStreamReader(System.in));
		writer = new BufferedWriter(
				new OutputStreamWriter(System.out));
		
		multilineSymbol = DEFAULT_MULTILINE_SYMBOL;
		morelinesSymbol = DEFAULT_MORELINES_SYMBOL;
		promptSymbol = DEFAULT_PROMPT_SYMBOL;
	}

	
	@Override
	public String readLine() throws IOException {
		return reader.readLine();
	}

	
	@Override
	public void write(String text) throws IOException {
		writer.write(text);
		writer.flush();
	}

	
	@Override
	public void writeln(String text) throws IOException {
		writer.write(text);
		writer.newLine();
		writer.flush();
	}

	
	@Override
	public Iterable<ShellCommand> commands() {
		return commands;
	}

	
	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	
	@Override
	public void setMultilineSymbol(Character symbol) {
		Objects.requireNonNull(symbol);
		multilineSymbol = symbol;
	}

	
	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	
	@Override
	public void setPromptSymbol(Character symbol) {
		Objects.requireNonNull(symbol);
		promptSymbol = symbol;
	}

	
	@Override
	public Character getMoreLinesSymbol() {
		return morelinesSymbol;
	}

	
	@Override
	public void setMoreLinesSymbol(Character symbol) {
		Objects.requireNonNull(symbol);
		morelinesSymbol = symbol;
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		reader.close();
		writer.close();
	}
}
