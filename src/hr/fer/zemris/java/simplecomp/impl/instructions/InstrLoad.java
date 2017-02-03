package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents a load instruction which takes 1 register and a memory address as
 * arguments. The instruction loads the value in the memory location given as a
 * number and stores it in the register given as the first argument. For
 * example, the command "load r0, 3" loads the value from the memory address at
 * location 3 and stores it in the register r0. The instruction doesn't allow
 * indirect addressing.
 * 
 * @author labramusic
 *
 */
public class InstrLoad implements Instruction {

	/**
	 * Index of the register which stores the value from the given memory
	 * location.
	 */
	private int indexOfRegister;

	/**
	 * Memory address which stores a value.
	 */
	private int memoryLocation;

	/**
	 * Initializes a new load instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given, the first argument is not a register, the second argument is
	 * not a number or if indirect addressing is used.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		InstructionArgument firstArgument = arguments.get(0);
		if (!firstArgument.isRegister() || RegisterUtil.isIndirect((Integer) firstArgument.getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		InstructionArgument secondArgument = arguments.get(1);
		if (!secondArgument.isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}

		this.indexOfRegister = RegisterUtil.getRegisterIndex((Integer) firstArgument.getValue());
		this.memoryLocation = (Integer) secondArgument.getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object storedValue = computer.getMemory().getLocation(memoryLocation);
		computer.getRegisters().setRegisterValue(indexOfRegister, storedValue);
		return false;
	}

}
