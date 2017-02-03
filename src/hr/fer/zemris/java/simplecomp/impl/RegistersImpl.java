package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents all the registers in the memory implementation. Contains
 * general-purpose registers, the program counter and a flag register. Values in
 * registers are stored as objects.
 * 
 * @author labramusic
 *
 */
public class RegistersImpl implements Registers {

	/**
	 * The values stored in the registers at disposal.
	 */
	private Object[] registerValues;

	/**
	 * The program counter which stores the memory address of the next
	 * instruction to be executed.
	 */
	private int programCounter;

	/**
	 * The flag register, set to false by default.
	 */
	private boolean flag;

	/**
	 * Initializes a new registers implementation with the given number of
	 * registers. Throws {@link IllegalArgumentException} if regsLen is less
	 * than the minimum number of registers, given by the value of
	 * STACK_REGISTER_INDEX+1.
	 * 
	 * @param regsLen
	 *            number of registers the microprocessor has on its disposition.
	 */
	public RegistersImpl(int regsLen) {
		if (regsLen < STACK_REGISTER_INDEX + 1) {
			throw new IllegalArgumentException("Number of registers cannot be less than " + (STACK_REGISTER_INDEX + 1)
					+ ". " + regsLen + " was given.");
		}
		registerValues = new Object[regsLen];
	}

	@Override
	public Object getRegisterValue(int index) {
		if (index < 0 || index >= registerValues.length) {
			throw new IndexOutOfBoundsException("Register doesn't exist.");
		}
		return registerValues[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		if (index < 0 || index >= registerValues.length) {
			throw new IndexOutOfBoundsException("Register doesn't exist.");
		}
		registerValues[index] = value;

	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;

	}

	@Override
	public void incrementProgramCounter() {
		++programCounter;

	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
