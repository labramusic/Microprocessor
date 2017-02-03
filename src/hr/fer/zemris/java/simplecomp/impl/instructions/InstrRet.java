package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instruction which returns from the subroutine called with the call
 * instruction. Expects no arguments. The address stored on the stack is set as
 * the value of the program counter. The only legal call of the instruction is
 * simply "ret".
 * 
 * @author labramusic
 *
 */
public class InstrRet implements Instruction {

	/**
	 * Initializes a new ret instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if the list is not empty.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		int location = 1 + (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location);

		Object value = computer.getMemory().getLocation(location);
		computer.getRegisters().setProgramCounter((Integer) value);
		return false;
	}

}
