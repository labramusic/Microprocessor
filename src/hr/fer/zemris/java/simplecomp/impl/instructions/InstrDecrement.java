package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents a decrement instruction which takes 1 register as an argument. The
 * instruction decrements the value stored in the register given as an argument
 * by 1. For example, the command "decrement r0" decrements the value in r0 by
 * 1. The instruction doesn't allow indirect addressing.
 * 
 * @author labramusic
 *
 */
public class InstrDecrement implements Instruction {

	/**
	 * Index of the register.
	 */
	private int indexOfRegister;

	/**
	 * Initializes a new decrement instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given, the given argument is not a register or indirect addressing is used.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		InstructionArgument argument = arguments.get(0);
		if (!argument.isRegister() || RegisterUtil.isIndirect((Integer)argument.getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.indexOfRegister = RegisterUtil.getRegisterIndex((Integer) argument.getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object registerValue = computer.getRegisters().getRegisterValue(indexOfRegister);
		computer.getRegisters().setRegisterValue(indexOfRegister, (Integer) registerValue - 1);
		return false;
	}

}
