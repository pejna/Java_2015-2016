package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Jump if true instruction of the assembler. Takes in 1 argument.
 * Takes in an address with location to jump to. If the flag register
 * is set to true, makes the jump.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrJumpiftrue implements Instruction {

	/**
	 * Address to jump to.
	 */
	private int address;


	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrJumpiftrue(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);

		if (arguments.size() != 1) {
			throw new IllegalArgumentException(
					"Warning - Expected 1 argument!");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + 0 + "!");
		}

		address= (Integer) arguments.get(0).getValue();
	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);
		if(computer.getRegisters().getFlag()){
			computer.getRegisters().setProgramCounter(address);
		}

		return false;
	}
}
