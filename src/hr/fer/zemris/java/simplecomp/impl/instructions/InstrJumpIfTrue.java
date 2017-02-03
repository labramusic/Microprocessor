package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents a jumpIfTrue instruction which takes one argument, a memory location given as a number.
 * The instruction stores the given location in the program counter, if the register flag
 * is set to true and such location exists. 
 * For example, the command "jumpIfTrue 10" stores the value 10 in the program counter only if the
 * register flag is set to true.
 * 
 * @author labramusic
 *
 */
public class InstrJumpIfTrue implements Instruction {

	/**
	 * Memory location to store in the program counter.
	 */
	private int memLocation;

	/**
	 * Initializes a new jumpIfTrue instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given or the given argument is not a number.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		InstructionArgument argument = arguments.get(0);
		if (!argument.isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		this.memLocation = (Integer)argument.getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Registers registers = computer.getRegisters();
		if (registers.getFlag() == true) {
			registers.setProgramCounter(memLocation);
		}
		return false;
	}

}
