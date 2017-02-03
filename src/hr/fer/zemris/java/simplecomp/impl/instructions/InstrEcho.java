package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Represents an echo instruction which takes 1 register as an argument. The
 * instruction prints the value stored in the register given as the first
 * argument on the standard output. For example, the command "echo r0" prints
 * "abc" on the standard output if the string "abc" is stored in r0. The
 * instruction allows indirect addressing, so a command such as "echo [r0+25]"
 * is also legal.
 * 
 * @author labramusic
 *
 */
public class InstrEcho implements Instruction {

	/**
	 * Index of the register.
	 */
	private int indexOfRegister;

	/**
	 * Offset from the given memory address.
	 */
	private int offset;

	/**
	 * Flag set to true if indirect addressing is used.
	 */
	private boolean isIndirect;

	/**
	 * Initializes a new echo instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given or the given argument is not a register.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		InstructionArgument argument = arguments.get(0);
		if (!argument.isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.indexOfRegister = RegisterUtil.getRegisterIndex((Integer) argument.getValue());
		if (RegisterUtil.isIndirect((Integer) argument.getValue())) {
			this.offset = RegisterUtil.getRegisterOffset((Integer) argument.getValue());
			this.isIndirect = true;
		}
	}

	@Override
	public boolean execute(Computer computer) {
		Object registerValue = computer.getRegisters().getRegisterValue(indexOfRegister);
		if (isIndirect) {
			Memory memory = computer.getMemory();
			Integer location = (Integer) registerValue + offset;
			System.out.print(memory.getLocation(location));
		} else {
			System.out.print(registerValue);
		}
		return false;
	}

}
