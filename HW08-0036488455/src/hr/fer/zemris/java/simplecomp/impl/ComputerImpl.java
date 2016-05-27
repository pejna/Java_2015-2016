package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * {@link Computer} implementation that holds memory and registers.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ComputerImpl implements Computer {

	/**
	 * Memory of the computer.
	 */
	private Memory memory;
	
	/**
	 * Registers of the computer processor.
	 */
	private Registers registers;
	
	
	/**
	 * Creates the computer with given memory and registry sizes.
	 * 
	 * @param memSize size of the memory
	 * @param registerNum number of registers to hold
	 * @throws IllegalArgumentException if given memory lesser than 1 or
	 * register count lesser than 16
	 */
	public ComputerImpl(int memSize, int registerNum) {
		memory = new MemoryImpl(memSize);
		registers = new RegistersImpl(registerNum);
	}

	/**
	 * @return retuns the memory held
	 */
	@Override
	public Memory getMemory() {
		return memory;
	}

	/**
	 * @return returns the registers held
	 */
	@Override
	public Registers getRegisters() {
		return registers;
	}

}
