package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Load instruction of the assembler. Takes in 2 arguments. First s a register,
 * the second is an address. Loads the Value at the given address into the given
 * register.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrLoad implements Instruction {

	/**
	 * Index of the register.
	 */
	private int registerIndex;

	/**
	 * Address with value.
	 */
	private int address;

	/**
	 * Position of register argument.
	 */
	private static final int REGISTER_POSITION = 0;

	/**
	 * Position of address argument.
	 */
	private static final int ADDRESS_POSITION = 1;


	/**
	 * Creates the instruction with according arguments.
	 * 
	 * @param arguments
	 *            arguments according to the class documentation
	 * @throws IllegalArgumentException
	 *             if argument types don't match
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);
		if (arguments.size() != 2) {
			throw new IllegalArgumentException(
					"Warning - Expected 2 arguments!");
		}

		if (!arguments.get(REGISTER_POSITION).isRegister()
				|| RegisterUtil.isIndirect((Integer) arguments
						.get(REGISTER_POSITION).getValue())) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + REGISTER_POSITION
							+ "!");
		}
		if (!arguments.get(ADDRESS_POSITION).isNumber()) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + ADDRESS_POSITION
							+ "!");
		}

		registerIndex = RegisterUtil.getRegisterIndex(
				(Integer) arguments.get(REGISTER_POSITION).getValue());
		address = (Integer) arguments.get(ADDRESS_POSITION).getValue();
	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);
		Object value = computer.getMemory().getLocation(address);
		computer.getRegisters().setRegisterValue(registerIndex, value);
		return false;
	}

}
