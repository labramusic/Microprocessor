package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents a push instruction which takes 1 register as an argument. The
 * instruction stores the value in the given register on the memory location to
 * which the stack pointer currently points, and then decrements the stack
 * pointer address by 1. For example, the command "push r0" puts the value in r0
 * on the memory address of the stack pointer and then then decrements the stack
 * pointer address by 1. The stack pointer address is given by the
 * STACK_REGISTER_INDEX constant in {@link Registers}. The instruction doesn't
 * allow indirect addressing.
 * 
 * @author labramusic
 *
 */
public class InstrPush implements Instruction {

	/**
	 * Index of the register.
	 */
	private int indexOfRegister;

	/**
	 * Initializes a new push instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given, the given argument is not a register or indirect addressing is used.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrPush(List<InstructionArgument> arguments) {
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
		int location = (Integer)computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		Object value = computer.getRegisters().getRegisterValue(indexOfRegister);

		computer.getMemory().setLocation(location, value);

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location - 1);
		return false;
	}

}
