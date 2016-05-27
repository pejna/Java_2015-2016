package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Pop instruction of the assembler. Takes in 1 register as argument. Popps the
 * value on the top of the stack and places it into the given register.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrPop implements Instruction {

	/**
	 * Index of the register.
	 */
	int registerIndex = 0;


	/**
	 * Creates the instruction with according arguments.
	 * 
	 * @param arguments
	 *            arguments according to the class documentation
	 * @throws IllegalArgumentException
	 *             if argument types don't match
	 */
	public InstrPop(List<InstructionArgument> arguments) {
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

		Integer address = (Integer) computer.getRegisters()
				.getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX,
				address + 1);

		Integer value = (Integer) computer.getMemory().getLocation(address + 1);

		computer.getRegisters().setRegisterValue(registerIndex, value);

		return false;
	}

}
