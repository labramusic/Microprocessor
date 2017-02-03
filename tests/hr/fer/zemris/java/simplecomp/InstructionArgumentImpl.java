package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * An implementation of an instruction argument used only for mock tests.
 * 
 * @author labramusic
 *
 */
public class InstructionArgumentImpl implements InstructionArgument {

	/**
	 * Value if argument is an integer.
	 */
	Integer intValue;

	/**
	 * Value if argument is a string.
	 */
	String stringValue;

	/**
	 * Value if argument is a register.
	 */
	Integer descriptor;

	/**
	 * Initializes a new InstructionArgumentImpl with the given string value.
	 * The current implementation, being used only for tests, doesn't support
	 * negative offsets. The behavior of the tested methods can be fully tested
	 * nonetheless.
	 * 
	 * @param value
	 *            string value
	 */
	public InstructionArgumentImpl(String value) {
		if (value.matches("r\\d+")) {
			descriptor = Integer.parseInt(value.substring(1, value.length()));
		} else if (value.matches("\\[r\\d+[+-]\\d+\\]")) {
			String[] split = value.split("\\+|\\-");
			int indirect = 0x01000000;
			int offset = Integer.parseInt(split[1].substring(0, split[1].length() - 1));
			offset = offset << 8;
			int index = Integer.parseInt(split[0].substring(2, split[0].length()));
			descriptor = indirect | offset | index;
		} else {
			stringValue = value;
		}
	}

	/**
	 * Initializes a new InstructionArgumentImpl with the given integer value.
	 * 
	 * @param value
	 *            integer value
	 */
	public InstructionArgumentImpl(Integer value) {
		intValue = value;
	}

	@Override
	public Object getValue() {
		if (isNumber())
			return intValue;
		if (isString())
			return stringValue;
		return descriptor;
	}

	@Override
	public boolean isNumber() {
		if (intValue == null)
			return false;
		return true;
	}

	@Override
	public boolean isRegister() {
		if (descriptor == null)
			return false;
		return true;
	}

	@Override
	public boolean isString() {
		if (stringValue == null)
			return false;
		return true;
	}

}
