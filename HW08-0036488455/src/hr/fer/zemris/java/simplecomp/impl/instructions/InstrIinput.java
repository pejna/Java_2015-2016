package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * IInput instruction of the assembler. Takes in 1 address as the argument.
 * Reads one line from standard input and stores it's content into the
 * given address. If it doesn't read a number it set's the flag register
 * to false and doesn't fill the address.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrIinput implements Instruction {

	/**
	 * Addres given.
	 */
	private int address;


	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);

		if (arguments.size() != 1) {
			throw new IllegalArgumentException(
					"Warning - Expected 1 argument!");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + 0 + "!");
		}

		address = (Integer) arguments.get(0).getValue();
	}

	
	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		String input = null;
		input = scanner.nextLine();

		boolean noProblem = true;
		try {
			Integer value = Integer.parseInt(input);
			computer.getMemory().setLocation(address, value);

		} catch (Exception e) {
			noProblem = false;
		}

		computer.getRegisters().setFlag(noProblem);

		// br not closed because System.in could be used by other classes
		return false;
	}

}
