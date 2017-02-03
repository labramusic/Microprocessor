package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Represents a multiply instruction which takes 3 registers as arguments. The
 * result of multiplying values stored in the registers given as the second two
 * arguments is stored in the register given as the first argument. For example,
 * the command "mul r0, r1, r2" multiplies the values in r1 and r2 and stores
 * the result in r0. The instruction doesn't allow indirect addressing.
 * 
 * @author labramusic
 *
 */
public class InstrMul implements Instruction {

	/**
	 * Index of register 1 which stores the result of multiplication.
	 */
	private int indexOfRegister1;

	/**
	 * Index of register 2 which stores the multiplier.
	 */
	private int indexOfRegister2;

	/**
	 * Index of register 3 which stores the multiplicand.
	 */
	private int indexOfRegister3;

	/**
	 * Initializes a new multiply instruction with the given list of arguments.
	 * Throws {@link IllegalArgumentException} if an invalid number of arguments is given,
	 * an argument is not a register, or if indirect addressing is used.
	 * 
	 * @param arguments
	 *            list of arguments
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + (i + 1) + "!");
			}
		}
		this.indexOfRegister1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexOfRegister2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexOfRegister3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexOfRegister2);
		Object value2 = computer.getRegisters().getRegisterValue(indexOfRegister3);
		computer.getRegisters().setRegisterValue(indexOfRegister1,
				Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;
	}

}
