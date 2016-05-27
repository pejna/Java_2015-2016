package hr.fer.zemris.java.hw07.crypto.commands;

import javax.crypto.Cipher;


/**
 * Decryption command for the Crypto program. The execute method
 *  acepts 3 {@link String}. First is the command name, there
 *  for easier use with {@link Crypto} class.
 *  The second is the path to the input file.
 *  The third is the path to the output file.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Decrypt implements CryptoCommand {

	/**
	 * Keyword of the command.
	 */
	private static final String KEYWORD = "decrypt";
	
	/**
	 * Crypter used to decrypt files.
	 */
	private Crypter crypter;
	
	
	/**
	 * Creates the {@link Decrypt} command.
	 */
	public Decrypt() {
		crypter = new Crypter(Cipher.DECRYPT_MODE);
	}
	
	@Override
	public void execute(String[] args) {
		crypter.execute(args);
	}

	@Override
	public String getKeyword() {
		return KEYWORD;
	}

}
