package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Move instruction of the assembler. Takes in 2 arguments. First argument
 * is a register with either indirect memory adress or holds the value
 * itself. Second argument is either memory adress, indirect address in a
 * register or a reigster holding the value. Moves the value referenced by
 * or held by the second into the first argument or the location it refers
 * to.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrMove implements Instruction {

	/**
	 * Value of the first argument.
	 */
	int value1;

	/**
	 * Offset of the location if first argument is indirect.
	 */
	int offset1;

	/**
	 * Is first argument indirect.
	 */
	boolean isIndirect1;

	/**
	 * Value of the second argument.
	 */
	int value2;

	/**
	 * Offset of the location if second argument is indirect.
	 */
	int offset2;

	/**
	 * Is second argument a register.
	 */
	boolean isRegister2;

	/**
	 * Is second argument indirect.
	 */
	boolean isIndirect2;

	/**
	 * Position of the origin argument.
	 */
	private static final int FROM = 1;

	/**
	 * Position of the goal argument.
	 */
	private static final int TO = 0;


	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);
		if (arguments.size() != 2) {
			throw new IllegalArgumentException(
					"Warning - Expected 2 arguments!");
		}

		if (!arguments.get(TO).isRegister()) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + TO + "!");
		}

		if (arguments.get(FROM).isString()) {
			throw new IllegalArgumentException(
					"Warning - Type mismatch for argument " + FROM + "!");
		}

		int register1 = (Integer) arguments.get(TO).getValue();

		value1 = RegisterUtil.getRegisterIndex(register1);
		if (RegisterUtil.isIndirect(register1)) {
			isIndirect1 = true;
			offset1 = RegisterUtil.getRegisterOffset(register1);
		} else {
			isIndirect1 = false;
		}

		if (arguments.get(FROM).isNumber()) {
			isRegister2 = false;
			value2 = (Integer) arguments.get(FROM).getValue();
			return;
		} else {
			isRegister2 = true;
		}

		int register2 = (Integer) arguments.get(FROM).getValue();

		value2 = RegisterUtil.getRegisterIndex(register2);
		if (RegisterUtil.isIndirect(register2)) {
			isIndirect2 = true;
			offset2 = RegisterUtil.getRegisterOffset(register2);
		} else {
			isIndirect2 = false;
		}

	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);

		Object value;

		if (!isRegister2) {
			value = value2;
		} else {
			if (isIndirect2) {
				int address = (Integer) computer.getRegisters()
						.getRegisterValue(value2);
				value = computer.getMemory().getLocation(address + offset2);
			} else {
				value = computer.getRegisters().getRegisterValue(value2);
			}
		}

		if (isIndirect1) {
			int address = (Integer) computer.getRegisters()
					.getRegisterValue(value1);
			computer.getMemory().setLocation(address + offset1, value);
		} else {
			computer.getRegisters().setRegisterValue(value1, value);
		}

		return false;
	}

}
