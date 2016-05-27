package hr.fer.zemris.java.hw07.crypto;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw07.crypto.commands.CheckSha;
import hr.fer.zemris.java.hw07.crypto.commands.CryptoCommand;
import hr.fer.zemris.java.hw07.crypto.commands.Decrypt;
import hr.fer.zemris.java.hw07.crypto.commands.Encrypt;


/**
 * Program used to encrypt, decrypt and ckeck changes by SHA-256
 * encoding. Takes in 2 or 3 parameters. First parameter is
 * the name of the command. Supports 3 commands
 * checksha - takes in 1 aditional argument, the path to the file
 * asks the user for comared to string afterwards
 * decrypt - takes in 2 aditional arguments, path to the file to be
 * decrypted and path to the decrypted file to be placed to
 * encrypt - takes in 2 aditional arguments, path to the file to be
 * encrypted and path to the encrypted file to be placed to
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Crypto {

	/**
	 * Map containing command matched with their keywords.
	 */
	private static Map<String, CryptoCommand> commands;
	
	/**
	 * String by the commands to aks used for input.
	 */
	public static final String INPUT_PROMPT = "> ";

	/**
	 * Commands accepted by the {@link Crypto}.
	 */
	private static final CryptoCommand[] ACCEPTED_COMMANDS = {
			new CheckSha(),
			new Decrypt(),
			new Encrypt()
	};
	
	/**
	 * Executes the program
	 * 
	 * @param args used according to the class javadoc decription
	 */
	public static void main(String[] args) {
		if(args.length < 1){
			System.err.println("Aborting - Command not given!");
			System.exit(-1);
		}
		
		commands = new HashMap<>();
		for(CryptoCommand command : ACCEPTED_COMMANDS){
			commands.put(command.getKeyword().toUpperCase(), command);
		}
		
		CryptoCommand command = determineCommand(args[0]);
		if(command == null){
			System.err.println("Aborting - "
					+ "Unrecognized command: " + args[0]);
			System.exit(-1);
		}
		
		command.execute(args);
		
	}
	
	/**
	 * Finds the command responsible for execution of desired
	 * actions.
	 * 
	 * @param string keyword of the command
	 * @return returns the command that matches the keyword or 
	 * null if none is found
	 */
	private static CryptoCommand determineCommand(String string){
		return commands.get(string.trim().toUpperCase());
	}

}
