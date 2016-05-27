package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;

/**
 * Hexdump command to be used with {@link Environment} implementing classes.
 * Writes out the hexadecimal code of the contents of the file at the given
 * location.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class HexdumpCommand extends AbstractCommand {

	/**
	 * Name of the command.
	 */
	private static final String NAME = "hexdump";

	/**
	 * Description of the command.
	 */
	private static final String[] DESCRIPTION = {
			" - produces a hex dump of the file ath the given path" };

	/**
	 * Size of the block of bytes.
	 */
	private static final int BLOCK_SIZE = 16;

	/**
	 * Character zero
	 */
	private static final char ZERO = '0';

	/**
	 * Character to represent address belonging.
	 */
	private static final String COLON = ":";

	/**
	 * Character to represent unsupported character representation.
	 */
	private static final char UNSUPPORTED_CHARACTER = '.';

	/**
	 * Space between hex numbers.
	 */
	private static final String SPACE = " ";

	/**
	 * Divider of hex output into 2.
	 */
	private static final char DIVIDER = '|';
	
	
	/**
	 * Creates the command.
	 */
	public HexdumpCommand() {
		super(NAME, DESCRIPTION);
	}

	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);

		try {
			if (arguments == null) {
				env.writeln("No arguments given!");
			}
			
			Path inputPath = Paths.get(arguments);
			if(!inputPath.toFile().exists()){
				env.writeln("Given file does not exist!");
				return ShellStatus.CONTINUE;
			}
			
			InputStream input = Files.newInputStream(inputPath,
					StandardOpenOption.READ); 
			
			int counter = 0;
			int read = 0;
			byte[] buffer = new byte[BLOCK_SIZE];
			
			while((read = input.read(buffer)) > 0){
				env.writeln(createHexLine(counter * BLOCK_SIZE, buffer, read));
			}
			
			
		} catch (IOException e) {
			try {
				env.writeln("Couldn't open the given path!");
			} catch (IOException ignorable) {}
		} catch (InvalidPathException e1){
			try {
				env.writeln("Couldn't deduce a path from the given arguments!");
			} catch (IOException ignorable) {}
			
		}
		
		return ShellStatus.CONTINUE;
	}

	
	/**
	 * Creates a line holding hexadecimal representations of the given array.
	 * Contains relative address in the file, hexadecimal characters and uft-8
	 * representation of supported characters.
	 * 
	 * @param address relative adress of the bytes in the file
	 * @param array array with 16 bytes
	 * @param length how many bytes in the array are used
	 * @return returns string containing information about given bytes
	 */
	private static String createHexLine(int address, byte[] array, int length){
		StringBuilder sb = new StringBuilder();
		
		sb.append(createAddress(Integer.toHexString(address)));
		sb.append(SPACE);
		
		sb.append(createHexValues(array, length));
		
		sb.append(DIVIDER);
		sb.append(SPACE);
		sb.append(createStringRepresentation(array, length));
		
		return sb.toString();
		
	}
	
	
	/**
	 * Creates an address for createHexLine from given string containing
	 * hexadecimal integer.
	 * @param string string with hexadecimal integer
	 * @return returns string in format required by createHexLine
	 */
	private static String createAddress(String string){
		int length = string.length();
		StringBuilder sb = new StringBuilder();
		
		int counter = 0;
		while(counter < BLOCK_SIZE/2 + length){
			sb.append(ZERO);
			counter++;
		}
		
		sb.append(string).append(COLON);
		
		return sb.toString();
	}
	
	
	/**
	 * Creates a string representation of given byte array.
	 * 
	 * @param array array to be represented
	 * @param length number of places used in the array
	 * @return returns string of the array
	 */
	private static String createStringRepresentation(byte[] array, int length){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < length ; i++){
			int representation = array[i];
			if(representation < 32 || representation > 127){
				sb.append(UNSUPPORTED_CHARACTER);
			} else {
				sb.append((char) array[i]);
			}
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Creates a string containing hexadecimal representation of bytes
	 * in the given array divided by spaces.
	 * 
	 * @param array array with bytes
	 * @param length number of bytes in the array used
	 * @return returns string with given bytes
	 */
	private static String createHexValues(byte[] array, int length){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < BLOCK_SIZE; i++){
			if(i >= length){
				sb.append("  ");
			} else {
				sb.append(String.format("%02X", array[i]));
			}
			if(i == BLOCK_SIZE/2 - 1){
				sb.append(DIVIDER);
			} else {
				sb.append(SPACE);
			}
		}
		
		return sb.toString();
	}
}
