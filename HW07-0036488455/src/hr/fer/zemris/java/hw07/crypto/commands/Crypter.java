package hr.fer.zemris.java.hw07.crypto.commands;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.zemris.java.hw07.crypto.Crypto;


/**
 * Class used to encrypt or decrypt files with AES algorith, and
 * handle inputs during execution of encryption and decryption
 * commands.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Crypter {

	/**
	 * Cipher used to encrypt/decrypt files.
	 */
	private Cipher cipher;

	/**
	 * Says whether the {@link Crypter} encrypts or decrypts.
	 */
	private int cryptMode;

	/**
	 * Algorithm by which to do the cryption.
	 */
	private static final String KEY_ALGORITHM = "AES";

	/**
	 * Transformation used to initialize the {@link Cipher}.
	 */
	private static final String CIPHER_TRANSFORMATION = KEY_ALGORITHM
			+ "/CBC/PKCS5Padding";

	/**
	 * Length of the accepted password.
	 */
	private static final int PASSWORD_LENGTH = 16;

	/**
	 * Prompt which asks for the password.
	 */
	private static final String PASSWORD_PROMPT = "Please provide "
			+ "password as hex-encoded text"
			+ "(16 bytes, i.e. 32 hex-digits):";

	/**
	 * Prompt which asks for initialization vector.
	 */
	private static final String INITIALIZATION_PROMPT = "Please "
			+ "provide initialization "
			+ "vector as hex-encoded text (32 hex-digits):";

	/**
	 * Format of a prompt that prints when finished. Requires
	 * the action name(Encrpytion/Decryption) output file name,
	 * and input file name.
	 */
	private static final String COMPLETED_PROMPT_FORMAT = 
			"%s completed. Generated file %s based on file %s.";

	/**
	 * Size of the block used by the buffers.
	 */
	private static final int BLOCK_SIZE = 4096;

	
	/**
	 * Creates the {@link Crypter} that either encrypts or decrypts
	 * based on the given argument.
	 * 
	 * @param cryptMode if given Cipher.DECRYPT_MODE will decrypt
	 * if given Cipher.ENCRYPT_MODE will encrypt data
	 */
	public Crypter(int cryptMode) {
		if(cryptMode != Cipher.DECRYPT_MODE 
				&& cryptMode != Cipher.ENCRYPT_MODE){
			throw new IllegalArgumentException("Warning - "
					+ "Unsupported cipher mode!");
		}
		
		this.cryptMode = cryptMode;

		try {
			cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);

		} catch (NoSuchAlgorithmException e) {
			System.err.println(
					"Aborting - " + "No cipher could be created "
							+ "from such transformation!");
			System.exit(-1);

		} catch (NoSuchPaddingException e) {
			System.err.println(
					"Aborting - " + "No cipher could be created "
							+ "from such transformation!");
			System.exit(-1);

		}
	}
	
	/*
	 * ******** Execution methods ************************************
	 */

	
	/**
	 * Executes the ecryption/decryption from and to the given files.
	 * Given array must have 3 {@link String}s otherwise aborts the
	 * program.
	 * 
	 * 
	 * @param args - First string is a command name and is here to 
	 * provide easier use with {@link Decrypt} and {@link Encrypt} 
	 * classes.
	 * Second {@link String} is the absolute or relative path
	 * of the starting file. 
	 * Third {@link String} is the absolute or relative path
	 * of the output file.
	 */
	public void execute(String[] args) {
		if (args.length != 3) {
			System.err.println("Aborting - "
					+ "Illegal number of arguments given, "
					+ "was expecting 3 but got " + args.length);
			System.exit(-1);
		}

		Path inputPath = Paths.get(args[1]);
		if (!inputPath.toFile().exists()) {
			throw new IllegalArgumentException("Warning - "
					+ "No file found at location: " + inputPath);
		}

		Path outputPath = Paths.get(args[2]);

		System.out.println(PASSWORD_PROMPT);
		System.out.print(Crypto.INPUT_PROMPT);

		Scanner scanner = new Scanner(System.in);
		String keyText = scanner.nextLine();

		System.out.println(INITIALIZATION_PROMPT);
		System.out.print(Crypto.INPUT_PROMPT);

		String ivText = scanner.nextLine();

		scanner.close();
		try {

		SecretKeySpec keySpec = new SecretKeySpec(
				hexToByteArray(keyText), KEY_ALGORITHM);
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(
				hexToByteArray(ivText));

			cipher.init(cryptMode, keySpec, paramSpec);

		} catch (InvalidKeyException
				| InvalidAlgorithmParameterException e) {

			System.err.println(
					"Aborting - " + "Couldn't initialize cipher!");
			System.exit(-1);
		} catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		transfer(inputPath, outputPath);

		concludeMessage(args[1], args[2]);
	}

	
	/*
	 * ******** Utility methods **************************************
	 */
	
	
	/**
	 * Creates a byte array holding the byte values from the
	 * hexadecimal formated string. Accepts only strings of length
	 * 32 characters.
	 * 
	 * @param hexString string containing hexadecimal numbers
	 * @return returns the array of byte values
	 */
	public static byte[] hexToByteArray(String hexString) {
		Objects.requireNonNull(hexString);
		
		int length = hexString.length();
		if (length != PASSWORD_LENGTH * 2) {
			throw new IllegalArgumentException("Aborting - "
					+ "Entered string " + hexString + 
					" is not of desired length!");
		}

		byte[] data = new byte[length / 2];

		for (int i = 0; i < length; i += 2) {
			data[i / 2] = (byte) ((Character
					.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}

		return data;
	}
	
	
	/**
	 * Encrypts/decrypts data from the input path file to the 
	 * output path file. Does not accept null as either
	 * of the paths.
	 * 
	 * @param inputPath path to the input file
	 * @param outputPath path to the output file
	 */
	private void transfer(Path inputPath, Path outputPath) {
		Objects.requireNonNull(inputPath);
		Objects.requireNonNull(outputPath);

		try (InputStream input = Files.newInputStream(inputPath,
				StandardOpenOption.READ);
				OutputStream output = new BufferedOutputStream(
						new FileOutputStream(outputPath.toFile()))) {
			
			int read = 0;
			byte[] inputBuffer = new byte[BLOCK_SIZE];
			
			while(true){
				read = input.read(inputBuffer);
				
				if(read < 1){
					output.write(cipher.doFinal());
					break;
				}
				
				output.write(cipher.update(inputBuffer, 0, read));
			}
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.err.println(
					"Aborting - " + "Couldn't open requested files!");
			System.exit(-1);
		} catch (IllegalBlockSizeException | 
				BadPaddingException e) {
			System.err.println("Aborting - "
					+ "Unable to finish requested action!");
			System.exit(-1);
		}
	}

	
	/**
	 * Prints out the message signaling the end of transfer from the
	 * given file to the given output file.
	 * 
	 * @param inputName name of the input file
	 * @param outputName name of the output file
	 */
	private void concludeMessage(String inputName, 
			String outputName){
		
		System.out
				.printf(COMPLETED_PROMPT_FORMAT,
						cryptMode == Cipher.DECRYPT_MODE
								? "Decryption" : "Encryption",
						outputName, inputName);
	}
}
