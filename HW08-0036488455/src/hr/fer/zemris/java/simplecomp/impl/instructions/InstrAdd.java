package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Assembly instruction that takes 3 register arguments, puts into the first
 * the sum of second two.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrAdd implements Instruction {

	/**
	 * Index of the first argument.
	 */
	private int registerIndex1;

	/**
	 * Index of the second argument.
	 */
	private int registerIndex2;

	/**
	 * Index of the third argument.
	 */
	private int registerIndex3;


	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil
					.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i + "!");
			}
		}
		this.registerIndex1 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndex2 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.registerIndex3 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(2).getValue());
	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(registerIndex2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(registerIndex3);
		computer.getRegisters().setRegisterValue(registerIndex1,
				Integer.valueOf((Integer) value1 + (Integer) value2));
		return false;
	}

}
