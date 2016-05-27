package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Multiply instruction of the assembler. Takes in 3 registers as arguments.
 * Multiplies the second two and places the given number into the first
 * argument.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrMul implements Instruction {

	/**
	 * Index of the first argument.
	 */
	private int indexRegistra1;
	
	/**
	 * Index of the second argument.
	 */
	private int indexRegistra2;
	
	/**
	 * Index of the third argument.
	 */
	private int indexRegistra3;


	/**
	 * Creates the instruction with according arguments.
	 * 
	 * @param arguments
	 *            arguments according to the class documentation
	 * @throws IllegalArgumentException
	 *             if argument types don't match
	 */
	public InstrMul(List<InstructionArgument> arguments) {
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
		this.indexRegistra1 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil
				.getRegisterIndex((Integer) arguments.get(2).getValue());
	}


	/**
	 * Executes the instruction.
	 */
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(indexRegistra1,
				Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;
	}
}
