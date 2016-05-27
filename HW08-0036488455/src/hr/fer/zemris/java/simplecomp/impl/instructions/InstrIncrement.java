package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Increment instruction of the assembler. Takes in 1 register as the argument.
 * Increments the value in the register by 1.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrIncrement implements Instruction {

	/**
	 * Index of the register.
	 */
	private int registerIndex;


	/**
	 * Creates the instruction with according arguments.
	 * 
	 * @param arguments
	 *            arguments according to the class documentation
	 * @throws IllegalArgumentException
	 *             if argument types don't match
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);

		if (arguments.size() != 1) {
			throw new IllegalArgumentException(
					"Warning - Expected 1 argument!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + 0 + "!");
		}

		int register = (Integer) arguments.get(0).getValue();

		if (RegisterUtil.isIndirect(register)) {
			throw new IllegalArgumentException(
					"Warning - Expected direct register!");
		}

		registerIndex = RegisterUtil.getRegisterIndex(register);
	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);

		Integer value = (Integer) computer.getRegisters()
				.getRegisterValue(registerIndex);
		computer.getRegisters().setRegisterValue(registerIndex, value + 1);

		return false;
	}
}
