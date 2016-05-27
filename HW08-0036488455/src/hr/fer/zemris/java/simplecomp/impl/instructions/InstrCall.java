package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Call instruction of the assembler. Takes in 1 function address. Places the
 * current program counter to the stack and jumps to the given address.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrCall implements Instruction {

	/**
	 * Address to jump to.
	 */
	int callAddress = 0;


	/**
	 * Creates the instruction with according arguments.
	 * 
	 * @param arguments
	 *            arguments according to the class documentation
	 * @throws IllegalArgumentException
	 *             if argument types don't match
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);

		if (arguments.size() != 1) {
			throw new IllegalArgumentException(
					"Warning - Expected 1 argument!");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + 0 + "!");
		}

		callAddress = (Integer) arguments.get(0).getValue();
	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);

		Integer value = (Integer) computer.getRegisters().getProgramCounter();

		Integer address = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getMemory().setLocation(address, value);

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX,
				address - 1);

		computer.getRegisters().setProgramCounter(callAddress);

		return false;
	}
}
