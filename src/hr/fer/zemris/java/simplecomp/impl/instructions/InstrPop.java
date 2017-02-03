package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents a pop instruction which takes 1 register as an argument. The
 * instruction increments the stack pointer address by 1, and then stores the
 * value from the address of the stack pointer to the given register. For
 * example, the command "pop r0" increments the stack pointer address by 1, and
 * then puts the value from the memory address of the stack pointer in r0. The
 * stack pointer address is given by the STACK_REGISTER_INDEX constant in
 * {@link Registers}. The instruction doesn't allow indirect addressing.
 * 
 * @author labramusic
 *
 */
public class InstrPop implements Instruction {

	/**
	 * Index of the register.
	 */
	private int indexOfRegister;

	/**
	 * Initializes a new pop instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given, the given argument is not a register or indirect addressing is
	 * used.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrPop(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		InstructionArgument argument = arguments.get(0);
		if (!argument.isRegister() || RegisterUtil.isIndirect((Integer) argument.getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.indexOfRegister = RegisterUtil.getRegisterIndex((Integer) argument.getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		int location = 1 + (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location);

		Object value = computer.getMemory().getLocation(location);
		computer.getRegisters().setRegisterValue(indexOfRegister, value);
		return false;
	}

}
