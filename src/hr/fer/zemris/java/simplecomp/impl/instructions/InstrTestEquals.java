package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Represents a testEquals instruction which takes 2 registers as arguments. The values in registers
 * given as arguments are compared. If the values are equal, the register flag is set to true,
 * otherwise it is set to false. For example, the command "testEquals r0, r1" sets the register flag
 * to true if both r0 and r1 contain the same value.
 * The instruction doesn't allow indirect addressing.
 * 
 * @author labramusic
 *
 */
public class InstrTestEquals implements Instruction {

	/**
	 * Index of register 1.
	 */
	private int indexOfRegister1;

	/**
	 * Index of register 2.
	 */
	private int indexOfRegister2;

	/**
	 * Initializes a new testEquals instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments
	 * is given, an argument is not a register, or if indirect addressing is
	 * used.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < 2; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + (i + 1) + "!");
			}
		}
		this.indexOfRegister1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexOfRegister2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Registers registers = computer.getRegisters();
		Object value1 = registers.getRegisterValue(indexOfRegister1);
		Object value2 = registers.getRegisterValue(indexOfRegister2);
		registers.setFlag(testEquals(value1, value2));
		return false;
	}

	/**
	 * Checks if two objects are equal.
	 * @param o1 first object
	 * @param o2 second object
	 * @return true if both objects are equal, false otherwise
	 */
	private static boolean testEquals(Object o1, Object o2) {
		if (o1 == null) {
			if (o2 == null) {
				return true;
			}
			return false;
		}
		return o1.equals(o2);
	}

}
