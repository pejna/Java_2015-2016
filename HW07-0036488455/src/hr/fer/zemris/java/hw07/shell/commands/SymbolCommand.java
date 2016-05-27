package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Symbol changing command used with {@link Environment} implementing
 * classes. Prints out the symbol with the given name, or changes
 * it to the new given symbol.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class SymbolCommand extends AbstractCommand {

	/**
	 * Map containing symbols paired with their names.
	 */
	private static Map<String, Character> symbols;

	/**
	 * Says if the symbols are initialized.
	 */
	private boolean initialized;

	/**
	 * Name of the more lines symbol.
	 */
	private static final String MORELINES_NAME = "MORELINES";
	
	/**
	 * Name of the multilines symbol.
	 */
	private static final String MULTILINE_NAME = "MULTILINE";
	
	/**
	 * Name of the prompt symbol.
	 */
	private static final String PROMPT_NAME = "PROMPT";
	
	/**
	 * Name of this command.
	 */
	private static final String NAME = "symbol";
	
	/**
	 * Description f this command
	 */
	private static final String[] DESCRIPTION = {
			" - if given the name of the "
			+ "symbol, shows the symbol with that name",
			" - if given the name and a different symbol, changes"
			+ "the symbol with that name to the given symbol"
	};

	
	
	/**
	 * Creates the command.
	 */
	public SymbolCommand() {
		super(NAME, DESCRIPTION);
		initialized = false;
		
	}

	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);
		
		if(!initialized){
			initializeSymbols(env);
			initialized = true;
		}
		
		if(arguments == null){
			try {
				env.writeln("No arguments given!");
				return ShellStatus.CONTINUE;
				
			} catch (IOException ignorable) {
				return ShellStatus.CONTINUE;
			}
		}
		
		String[] args = arguments.split(" ");
		
		if(args.length == 1){
			printSymbol(env, args[0]);
			
		} else if(args.length == 2){
			changeSymbol(env, args[0], args[1]);
			
		} else {
			try {
				env.writeln("Too many arguments given!");
			} catch (IOException e) {}
		}
		
		return ShellStatus.CONTINUE;
	}

	
	/**
	 * Prints the requested symbol to the given environment.
	 * 
	 * @param env envirojment to be printed to
	 * @param string name of the symbol
	 */
	private void printSymbol(Environment env, String string) {
		Character symbol = symbols.get(string.toUpperCase());
		
		if(symbol == null){
			unrecognizedSymbol(env);
			return;
		}
		
		try {
			env.writeln("Symbol for " + 
					string + " is '" + symbol + "'.");
			
		} catch (IOException ignorable) {}
	}

	
	/**
	 * Changes the requested symbol to the given symbol in the given 
	 * environment.
	 * 
	 * @param env environment containing symbols
	 * @param name name of the symbol to be changed
	 * @param newSymbolString symbol to be changed with
	 */
	private void changeSymbol(Environment env, String name, 
			String newSymbolString) {
		Character symbol = symbols.get(name.toUpperCase());
		
		if(symbol == null){
			unrecognizedSymbol(env);
			return;
		}
		
		if(newSymbolString.length() != 1){
			try {
				env.writeln("Given exchange symbol is too long! "
						+ "Must have only 1 character!");
			} catch (IOException e) {}
			return;
		}
		
		Character newSymbol = newSymbolString.charAt(0);
		
		symbols.put(name.toUpperCase(), newSymbol);
		
		if(name.toUpperCase().equals(MORELINES_NAME)){
			env.setMoreLinesSymbol(newSymbol);
		
		} else if(name.toUpperCase().equals(MULTILINE_NAME)){
			env.setMultilineSymbol(newSymbol);
		
		} else {
			env.setPromptSymbol(newSymbol);
		}
		
		try {
			env.writeln("Symbol for " + name.toUpperCase() 
					+ "changed from '" + symbol + "' to '" 
					+ newSymbol + "'.");
		} catch (IOException ignorable) {}
	}

	
	/**
	 * Prints the symbol is unrecognized to the given 
	 * {@link Environment}.
	 * 
	 * @param env environment to be printed to
	 */
	private void unrecognizedSymbol(Environment env){
		StringBuilder sb = new StringBuilder();
		
		for(String key : symbols.keySet()){
			sb.append(key).append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append("!");
		
		try {
			env.writeln("Unrecognized symbol, "
					+ "please use one of these: ");
			env.writeln(sb.toString());
			
		} catch (IOException ignorable) {}
	}
	
	
	/**
	 * Initializes the symbols with the symbols from the given
	 * environment.
	 * 
	 * @param env environment containing the symbols
	 */
	private void initializeSymbols(Environment env){
		Objects.requireNonNull(env);
		symbols = new HashMap<>();
		
		symbols.put(MORELINES_NAME.toUpperCase(), 
				env.getMoreLinesSymbol());
		symbols.put(MULTILINE_NAME.toUpperCase(), 
				env.getMultilineSymbol());
		symbols.put(PROMPT_NAME.toUpperCase(), 
				env.getPromptSymbol());
	}
}
