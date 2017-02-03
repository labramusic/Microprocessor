package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents a move instruction which takes 2 registers or a register and a
 * memory location as arguments. The instruction takes the value stored in the
 * second register or the given memory location and stores it in the first
 * register. For example, the command "move r0, r1" stores the value stored in
 * r1 into r0. Also, "move r0, 24" takes the value stored in the memory location
 * at 24 and stores it into r0. The instruction allows indirect addressing for
 * both arguments.
 * 
 * @author labramusic
 *
 */
public class InstrMove implements Instruction {

	/**
	 * The first argument.
	 */
	private InstructionArgument argument1;

	/**
	 * The second argument.
	 */
	private InstructionArgument argument2;

	/**
	 * Initializes a new move instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given, if the first argument is not a register or if the second
	 * argument is not a register or a number.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		argument1 = arguments.get(0);
		if (!argument1.isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		argument2 = arguments.get(1);
		if (!argument2.isRegister() && !argument2.isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		Object value = null;
		// get the value from the second argument
		if (argument2.isRegister()) {
			value = getValueFromRegister(computer, argument2);
		} else if (argument2.isNumber()) {
			value = argument2.getValue();
		}

		// store the value in the location the first argument points to
		Integer descriptor = (Integer) argument1.getValue();
		int index = RegisterUtil.getRegisterIndex(descriptor);
		if (RegisterUtil.isIndirect(descriptor)) {
			Object registerValue = computer.getRegisters().getRegisterValue(index);
			int offset = RegisterUtil.getRegisterOffset(descriptor);
			Integer location = (Integer) registerValue + offset;
			computer.getMemory().setLocation(location, value);
		} else {
			computer.getRegisters().setRegisterValue(index, value);
		}
		return false;
	}

	/**
	 * Gets the value stored in a register, or the value stored at the memory
	 * location at which the register and its offset points if indirect
	 * addressing is used.
	 * 
	 * @param computer
	 *            the computer
	 * @param register
	 *            the register
	 * @return value stored in register or indirectly addressed memory location
	 */
	private static Object getValueFromRegister(Computer computer, InstructionArgument register) {
		Integer descriptor = (Integer) register.getValue();
		int index = RegisterUtil.getRegisterIndex(descriptor);
		Object registerValue = computer.getRegisters().getRegisterValue(index);
		if (RegisterUtil.isIndirect(descriptor)) {
			int offset = RegisterUtil.getRegisterOffset(descriptor);
			Integer location = (Integer) registerValue + offset;
			return computer.getMemory().getLocation(location);
		}
		return registerValue;
	}

}
