package hr.fer.zemris.java.simplecomp;

import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Simulator of a processor found in computers. Takes in 1 argument from the
 * command line, the location of file with the code written. If not given a path
 * will prompt user to give it one.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Simulator {

	/**
	 * Runs the program.
	 * 
	 * @param args
	 *            location of the file with pseudo-assembly code
	 */
	public static void main(String[] args) {
		String path = args[0];
		if (args.length != 1) {
			path = getInput();
		}

		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");

		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(path, comp, creator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		exec.go(comp);

	}


	/**
	 * Gets the input from the standard input.
	 * 
	 * @return return return input from standard input
	 */
	private static String getInput() {
		System.out.println(
				"Please input the path to the desired pseudo-assembly file.");
		System.out.print("> ");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		
		return input;
	}
}
