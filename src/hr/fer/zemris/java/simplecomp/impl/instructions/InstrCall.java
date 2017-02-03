package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents a call instruction which takes one argument, a memory location
 * given as a number. The instruction calls the subroutine on the given address.
 * The current program counter content is stored on the stack, and then the
 * given address is stored in the program counter, defining the next instruction
 * to be executed. For example, the command "call 10" calls the subroutine on
 * address 10.
 * 
 * @author labramusic
 *
 */
public class InstrCall implements Instruction {

	/**
	 * Memory location to store in the program counter.
	 */
	private int memLocation;

	/**
	 * Initializes a new call instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given or the given argument is not a number.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		InstructionArgument argument = arguments.get(0);
		if (!argument.isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		this.memLocation = (Integer) argument.getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		int location = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		Object pcValue = computer.getRegisters().getProgramCounter();
		computer.getMemory().setLocation(location, pcValue);

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location - 1);

		computer.getRegisters().setProgramCounter(memLocation);
		return false;
	}

}
