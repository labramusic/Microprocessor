package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * An implementation of {@link Computer} which represents the data structure of
 * a computer.
 * 
 * @author labramusic
 *
 */
public class ComputerImpl implements Computer {

	/**
	 * Computer memory.
	 */
	private Memory memory;

	/**
	 * The registers of the computer.
	 */
	private Registers registers;

	/**
	 * Initializes a new computer implementation with the given memory size and
	 * number of registers.
	 * 
	 * @param size
	 *            memory size
	 * @param regsLen
	 *            number of registers
	 */
	public ComputerImpl(int size, int regsLen) {
		memory = new MemoryImpl(size);
		registers = new RegistersImpl(regsLen);
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

}
