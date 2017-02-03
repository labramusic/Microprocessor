package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * An implementation of computer memory which stores instances of {@link Object}
 * on its memory locations. Empty memory has <code>null</code> stored on all of
 * its locations.
 * 
 * @author labramusic
 *
 */
public class MemoryImpl implements Memory {

	/**
	 * Values stored at memory locations.
	 */
	private Object[] memoryValues;

	/**
	 * Initializes a new memory implementation with the given memory size. The
	 * size of the memory represents the number of memory locations. The given
	 * size must be greater than 0, or else {@link IllegalArgumentException} is
	 * thrown.
	 * 
	 * @param size
	 *            memory size
	 */
	public MemoryImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Memory must be greater than 0. Given size is " + size + ".");
		}
		memoryValues = new Object[size];
	}

	@Override
	public Object getLocation(int location) {
		if (location < 0 || location >= memoryValues.length) {
			throw new IndexOutOfBoundsException("Memory location doesn't exist.");
		}
		return memoryValues[location];
	}

	@Override
	public void setLocation(int location, Object value) {
		if (location < 0 || location >= memoryValues.length) {
			throw new IndexOutOfBoundsException("Memory location doesn't exist.");
		}
		memoryValues[location] = value;
	}

}
