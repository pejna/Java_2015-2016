package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Halt instruction of the assembler. Takes in no arguments.
 * Stops the execution of the program.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrHalt implements Instruction{

	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);
		
		if (arguments.size() != 0) {
			throw new IllegalArgumentException(
					"Warning - Expected 1 argument!");
		}
	}
	
	
	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
