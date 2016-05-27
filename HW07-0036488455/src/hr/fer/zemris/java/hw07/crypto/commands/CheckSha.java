package hr.fer.zemris.java.hw07.crypto.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import hr.fer.zemris.java.hw07.crypto.Crypto;


/**
 * CheckSha command for the Crypto program. The execute method
 *  acepts 3 {@link String}. First is the command name, there
 *  for easier use with {@link Crypto} class.
 *  The second is the path to the file who's sha encoding we check.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class CheckSha implements CryptoCommand {

	/**
	 * {@link MessageDigest} used to diges the file.
	 */
	private static MessageDigest digest;

	/**
	 * Keyword of the command.
	 */
	private static final String KEYWORD = "checksha";

	/**
	 * Block size of the buffers used.
	 */
	private static final int BLOCK_SIZE = 4096;

	/**
	 * String that requests input for digest comparison.
	 */
	private static final String REQUEST_INPUT = 
			"Please provide expected sha-256 digest for ";

	/**
	 * String that signifies the end of comparison.
	 */
	private static final String COMPLETE_PROMPT = 
			"Digesting completed. ";
	
	/**
	 * String that signifies that encoding is the same
	 * as the given string.
	 */
	private static final String APPROVAL_MESSAGE_FORMAT =
			"Digest of %s matches expected digest.";

	/**
	 * String that signifies that encoding is the different
	 * from the given string.
	 */
	private static final String DISSAPROVAL_MESSAGE_FORMAT = "Digest "
			+ "of %s does not match the expected digest. "
			+ "Digest was:%n%s";


	/**
	 * Creates the {@link CheckSha} command.
	 */
	public CheckSha() {
		try {
			digest = MessageDigest.getInstance("SHA-256");
			
		} catch (NoSuchAlgorithmException e) {
			
			System.err.println("Aborting - "
					+ "No algorithm by that name found!");
			System.exit(-1);
		}
	}

	
	/*
	 * ******** Execution methods ************************************
	 */
	
	
	@Override
	public void execute(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException(
					"Warning - " + "Unexpected number of arguments!");
		}

		Path path = Paths.get(args[1]);

		if (!path.toFile().exists()) {
			throw new IllegalArgumentException("Warning - "
					+ "No file found at location: " + path);
		}

		fillDigest(path);
		
		String input = getInput(args[1]);
		
		System.out.print(COMPLETE_PROMPT);
		
		String digested = byteArrayToString(digest.digest());
		
		if(digested.toString().equals(input)){
			System.out.printf(APPROVAL_MESSAGE_FORMAT, args[1]);
			
		} else {
			System.out.printf(DISSAPROVAL_MESSAGE_FORMAT, 
					args[1], digested);
		}
		
	}
	
	/*
	 * ******** Utility methods **************************************
	 */
	
	
	/**
	 * Fills the {@link MessageDigest} with the contents of the
	 * file in the given path.
	 * @param path path of the file
	 */
	private void fillDigest(Path path){
		try (InputStream stream = Files.newInputStream(path,
				StandardOpenOption.READ)) {

			byte[] buffer = new byte[BLOCK_SIZE];
			int read = 0;
			
			while (true) {
				read = stream.read(buffer);
				if(read < 1){
					break;
				}

				digest.update(buffer, 0, read);
			}

		} catch (IOException e) {
			System.err.println("Aborting - "
					+ "A mistake happened while working with file!");
			System.exit(-1);
		}
	}

	
	/**
	 * Requests and procures the compared to string from the standard
	 * input.
	 * 
	 * @param name of the file, used in requesting prompt
	 * @return returns the procured string
	 */
	private String getInput(String name){
		System.out.println(REQUEST_INPUT + name + ":");
		System.out.print(Crypto.INPUT_PROMPT);
		
		Scanner reader = new Scanner(System.in);
		String input = reader.nextLine().trim();
		
		reader.close();
		return input;
	}
	
	
	/**
	 * Transforms the byte array to a hexadecimally formated 
	 * {@link String}.
	 * 
	 * @param array array to be transformed
	 * @return transformed array
	 */
	private String byteArrayToString(byte[] array){
		StringBuilder sb = new StringBuilder();
		
		for(byte b : array){
			String hex = Integer.toHexString(0xFF & b);
			if(hex.length() == 1){
				sb.append("0");
			}
			sb.append(hex);
		}
		
		return sb.toString();
	}
	
	
	@Override
	public String getKeyword() {
		return KEYWORD;
	}

}
