package hr.fer.zemris.java.simplecomp.impl;

import java.util.Objects;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * {@link ExecutionUnit} implementation that acts as a processor for
 * {@link Computer} implementing classes, managing instructions and using it's
 * {@link Memory} and {@link Registers} for it's own good.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	/**
	 * Registers the of the current computer to be executed.
	 */
	private Registers registers;

	/**
	 * Memory of the current computer to be executed.
	 */
	private Memory memory;

	/**
	 * Starting location of instructions in the memory.
	 */
	private static final int STARTING_INSTRUCTON_LOCATION = 0;


	/**
	 * Runs the given computer through it's instructions.
	 * 
	 * @param computer
	 *            computer with memory filled with {@link Instruction} and
	 *            registers to be used
	 * @return returns true if execution went well, false otherwise
	 */
	@Override
	public boolean go(Computer computer) {
		Objects.requireNonNull(computer);

		try {
			registers = computer.getRegisters();
			memory = computer.getMemory();

			registers.setProgramCounter(STARTING_INSTRUCTON_LOCATION);

			while (true) {
				Instruction instruction = (Instruction) memory
						.getLocation(registers.getProgramCounter());
				registers.incrementProgramCounter();

				if (instruction.execute(computer)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	

		return true;
	}

}
