package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Assembler instructio, takes in two registers and sets the flag register
 * to true if given registers are equal, or false if not equal.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrTestequals implements Instruction {

	/**
	 * Index of the first register.
	 */
	private int registerIndex1;

	/**
	 * Index of the second register.
	 */
	private int registerIndex2;


	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrTestequals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < 2; i++) {
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
	}


	/**
	 * Executes the instruction.
	 */
	@Override
	public boolean execute(Computer computer) {
		Objects.requireNonNull(computer);

		Object register1 = computer.getRegisters()
				.getRegisterValue(registerIndex1);
		Object register2 = computer.getRegisters()
				.getRegisterValue(registerIndex2);
		
		computer.getRegisters().setFlag(register1.equals(register2));
		
		return false;
	}

}
