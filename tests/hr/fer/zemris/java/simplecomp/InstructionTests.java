package hr.fer.zemris.java.simplecomp;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPush;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class InstructionTests {

	@Mock
	private Computer computer;

	@Mock
	private Memory memory;

	@Mock
	private Registers registers;

	@Mock
	private InstructionArgument argument1;

	@Mock
	private InstructionArgument argument2;

	@Test
	public void testLoad() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(memory.getLocation(10)).thenReturn(Integer.valueOf(5));
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0);
		when(argument2.isNumber()).thenReturn(true);
		when(argument2.getValue()).thenReturn(10);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		list.add(argument2);
		Instruction load = new InstrLoad(list);

		boolean exec = load.execute(computer);
		assertFalse(exec);

		verify(computer).getMemory();
		verify(computer).getRegisters();
		verify(memory).getLocation(10);
		verify(registers).setRegisterValue(0, 5);
	}

	@Test
	public void testMoveWithRegisterAndMemoryLocation() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0);
		when(argument2.isNumber()).thenReturn(true);
		when(argument2.getValue()).thenReturn(10);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		list.add(argument2);
		Instruction move = new InstrMove(list);

		boolean exec = move.execute(computer);
		assertFalse(exec);

		verify(computer).getRegisters();
		verify(registers).setRegisterValue(0, 10);
	}

	@Test
	public void testMoveWithIndirectRegisterAndMemoryLocation() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(1)).thenReturn(3);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0x1000201);
		when(argument2.isNumber()).thenReturn(true);
		when(argument2.getValue()).thenReturn(10);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		list.add(argument2);
		Instruction move = new InstrMove(list);

		boolean exec = move.execute(computer);
		assertFalse(exec);

		verify(computer).getMemory();
		verify(memory).setLocation(5, 10);
	}

	@Test
	public void testMoveWithRegisterAndRegister() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(2)).thenReturn(4);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(1);
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(2);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		list.add(argument2);
		Instruction move = new InstrMove(list);

		boolean exec = move.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getRegisters();
		verify(registers).setRegisterValue(1, 4);
	}

	@Test
	public void testMoveWithRegisterAndIndirectRegister() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(2)).thenReturn(3);
		when(memory.getLocation(5)).thenReturn(20);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(1);
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(0x1000202);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		list.add(argument2);
		Instruction move = new InstrMove(list);

		boolean exec = move.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getRegisters();
		verify(computer, atLeastOnce()).getMemory();
		verify(memory).getLocation(5);
		verify(registers).setRegisterValue(1, 20);
	}

	@Test
	public void testMoveWithIndirectRegisterAndRegister() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(1)).thenReturn(3);
		when(registers.getRegisterValue(2)).thenReturn(7);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0x1000201);
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(2);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		list.add(argument2);
		Instruction move = new InstrMove(list);

		boolean exec = move.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getRegisters();
		verify(computer, atLeastOnce()).getMemory();
		verify(registers).getRegisterValue(2);
		verify(memory).setLocation(5, 7);
	}

	@Test
	public void testMoveWithIndirectRegisterAndIndirectRegister() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(1)).thenReturn(3);
		when(registers.getRegisterValue(2)).thenReturn(15);
		when(memory.getLocation(20)).thenReturn(8);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0x1000201);
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(0x1000502);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		list.add(argument2);
		Instruction move = new InstrMove(list);

		boolean exec = move.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getRegisters();
		verify(computer, atLeastOnce()).getMemory();
		verify(memory).getLocation(20);
		verify(memory).setLocation(5, 8);
	}

	@Test
	public void testPush() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(10);
		when(registers.getRegisterValue(0)).thenReturn(5);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		Instruction push = new InstrPush(list);

		boolean exec = push.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		verify(registers).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers).getRegisterValue(0);
		verify(memory).setLocation(10, 5);
		verify(registers).setRegisterValue(Registers.STACK_REGISTER_INDEX, 9);
	}

	@Test
	public void testPop() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(10);
		when(memory.getLocation(11)).thenReturn(2);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		Instruction pop = new InstrPop(list);

		boolean exec = pop.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		verify(registers).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers).setRegisterValue(Registers.STACK_REGISTER_INDEX, 11);
		verify(memory).getLocation(11);
		verify(registers).setRegisterValue(0, 2);
	}

	@Test
	public void testCall() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(10);
		when(registers.getProgramCounter()).thenReturn(1);
		when(argument1.isNumber()).thenReturn(true);
		when(argument1.getValue()).thenReturn(5);

		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1);
		Instruction call = new InstrCall(list);

		boolean exec = call.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		verify(registers).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers).getProgramCounter();
		verify(memory).setLocation(10, 1);
		verify(registers).setRegisterValue(Registers.STACK_REGISTER_INDEX, 9);
		verify(registers).setProgramCounter(5);
	}

	@Test
	public void testRet() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(10);
		when(memory.getLocation(11)).thenReturn(1);

		List<InstructionArgument> list = new ArrayList<>();
		Instruction ret = new InstrRet(list);

		boolean exec = ret.execute(computer);
		assertFalse(exec);

		verify(computer, atLeastOnce()).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		verify(registers).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers).setRegisterValue(Registers.STACK_REGISTER_INDEX, 11);
		verify(memory).getLocation(11);
		verify(registers).setProgramCounter(1);
	}

}
