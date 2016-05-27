package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;


/**
 * Pops the stack and places the popped value as the program counter.
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class InstrRet implements Instruction {

	
	/**
	 * Creates the instruction with according arguments.
	 * @param arguments arguments according to the class documentation
	 * @throws IllegalArgumentException if argument types don't match 
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		Objects.requireNonNull(arguments);

		if (arguments.size() != 0) {
			throw new IllegalArgumentException(
					"Warning - Expected no arguments!");
		}
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

		computer.getRegisters().setProgramCounter(value);


		return false;
	}

}
