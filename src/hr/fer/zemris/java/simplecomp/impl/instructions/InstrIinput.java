package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents a iinput instruction which takes one argument, a memory location
 * given as a number. The instruction reads from the standard input, interprets
 * the input as an integer and stores it in the given memory location. If conversion
 * succeeded, the register flag is set to true and to false if an exception occurred.
 * For example, the command "iinput 10" takes input from user and tries to store the
 * input integer on the memory location 10.
 * 
 * @author labramusic
 *
 */
public class InstrIinput implements Instruction {

	/**
	 * Memory location to store input integer.
	 */
	private int memLocation;

	/**
	 * Initializes a new iinput instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given or the given argument is not a number.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
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
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		try {
			Integer value = Integer.valueOf(sc.nextLine());
			computer.getMemory().setLocation(memLocation, value);
			computer.getRegisters().setFlag(true);
		} catch (Exception e) {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}
