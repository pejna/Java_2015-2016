package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Echo instruction of the assembler. Takes in 1 register as the argument.
 * Writes out the constents of the address pointed to by the given register if
 * indirect or the contents of the register if direct.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrEcho implements Instruction {

	/**
	 * Index of the register.
	 */
	private int registerIndex;

	/**
	 * Offset of the address if argument is indirect.
	 */
	private int offset;

	/**
	 * Is argument direct.
	 */
	private boolean isDirect;


	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
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
			offset = RegisterUtil.getRegisterOffset(register);
			isDirect = false;
		} else {
			isDirect = true;
		}

		registerIndex = RegisterUtil.getRegisterIndex(register);
	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);

		if (isDirect) {
			Object value = computer.getRegisters()
					.getRegisterValue(registerIndex);
			System.out.print(value);

		} else {
			int location = (Integer) computer.getRegisters()
					.getRegisterValue(registerIndex);

			System.out
					.print(computer.getMemory().getLocation(location + offset));
		}

		return false;
	}

}
