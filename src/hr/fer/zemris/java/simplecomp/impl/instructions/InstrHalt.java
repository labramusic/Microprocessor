package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instruction which halts the processor. Expects no arguments.
 * The only legal call of the instruction is simply "halt".
 * @author labramusic
 *
 */
public class InstrHalt implements Instruction {

	/**
	 * Initializes a new halt instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if the list is not empty.
	 * @param arguments list of arguments
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
