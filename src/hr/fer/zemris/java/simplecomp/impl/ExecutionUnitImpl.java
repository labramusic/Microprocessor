package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Execution unit implementation which runs the program.
 * @author labramusic
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		Memory memory = computer.getMemory();
		Registers registers = computer.getRegisters();
		registers.setProgramCounter(0);
		while (true) {
			Instruction instruction = (Instruction)memory.getLocation(registers.getProgramCounter());
			registers.incrementProgramCounter();
			if (instruction.execute(computer)) {
				break;
			}
		}
		return true;


	}

}
