package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Represents the memory used by {@link Computer} implementing classes. Can hold
 * various types of objects.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class MemoryImpl implements Memory {

	/**
	 * Array with memory locations.
	 */
	private Object[] memory;


	/**
	 * Creates the memory with the given integer as it's size.
	 * 
	 * @param size
	 *            size of the memory
	 * @throws IllegalArgumentException
	 *             if given number lesser than 1
	 */
	public MemoryImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException(
					"Warning - Cannot have no or negative memory space!");
		}

		memory = new Object[size];
	}


	/**
	 * Gets the value from the requested location.
	 * 
	 * @param location
	 *            memory adress
	 * @throws IndexOutOfBoundsException
	 *             if given locaton is not in the memory
	 * @return returns the value at requested location
	 */
	@Override
	public Object getLocation(int location) {
		if (location < 0 || location >= memory.length) {
			throw new IndexOutOfBoundsException();
		}

		return memory[location];
	}


	/**
	 * Sets the given value to the given location.
	 * 
	 * @param location
	 *            memory adress
	 * @param value
	 *            value to be placed in the given adress
	 * @throws IndexOutOfBoundsException
	 *             if given location is not in the memory
	 */
	@Override
	public void setLocation(int location, Object value) {
		if (location < 0 || location >= memory.length) {
			throw new IndexOutOfBoundsException();
		}

		memory[location] = value;
	}

}
