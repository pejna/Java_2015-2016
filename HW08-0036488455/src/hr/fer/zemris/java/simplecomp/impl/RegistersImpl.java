package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Register implementation to be used by {@link Computer} implementing classes.
 * Holds designated number of regisers no smaller than 16 with 16th being the
 * stack register. Also holds flag register so it can be used if needed and
 * program counter to count current memory location.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class RegistersImpl implements Registers {

	/**
	 * Registers kept.
	 */
	private Object[] registers;

	/**
	 * Flag register to be used if needed.
	 */
	private boolean flag;

	/**
	 * Holds current location in the file.
	 */
	private int programCounter;


	/**
	 * Creates the {@link RegistersImpl} with given number of registers no
	 * lesser than 16.
	 * 
	 * @param regsNum
	 *            number of registers to hold
	 * @throws IllegalArgumentException
	 *             if given number smaller than 16
	 */
	public RegistersImpl(int regsNum) {
		if (regsNum < Registers.STACK_REGISTER_INDEX + 1) {
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have less than " + Registers.STACK_REGISTER_INDEX
					+ 1 + " registers!");
		}

		registers = new Object[regsNum];
		programCounter = 0;
		flag = false;
	}


	/**
	 * @return returns the flag register.
	 */
	@Override
	public boolean getFlag() {
		return flag;
	}


	/**
	 * @return returns the program counter.
	 */
	@Override
	public int getProgramCounter() {
		return programCounter;
	}


	/**
	 * Gets the register value at the requested index.
	 * 
	 * @param index
	 *            index of the register holding value requested
	 * @throws IndexOutOfBoundsException
	 *             if given no register present with the given index
	 * @return returns the object at requested location            
	 */
	@Override
	public Object getRegisterValue(int index) {
		if (index < 0 || index >= registers.length) {
			throw new IndexOutOfBoundsException();
		}

		return registers[index];
	}


	/**
	 * Increments the program counter by 1.
	 */
	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}


	/**
	 * Sets the flag register to the given value.
	 * 
	 * @param value
	 *            new value of the flag register
	 */
	@Override
	public void setFlag(boolean value) {
		flag = value;
	}


	/**
	 * Sets the program counter to the given value.
	 * 
	 * @param value
	 *            new value of the program counter
	 * @throws IllegalArgumentException
	 *             if given value lesser than 0
	 */
	@Override
	public void setProgramCounter(int value) {
		if (value < 0) {
			throw new IllegalArgumentException(
					"Warning - Cannot count from lesser than 0!");
		}

		programCounter = value;
	}


	/**
	 * Sets the requested register to the given value.
	 * 
	 * @param index
	 *            index of the register
	 * @param value
	 *            value the register to be set to
	 * @throws IndexOutOfBoundsException
	 *             if no register by given index exists
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		if (index < 0 || index >= registers.length) {
			throw new IndexOutOfBoundsException();
		}
		registers[index] = value;
	}

}
