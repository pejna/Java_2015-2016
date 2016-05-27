
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Tests the InstrMove class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class MoveTest {

	@Mock
	private Computer computer;

	@Mock
	private Memory memory;

	@Mock
	private Registers registers;

	@Mock
	private List<InstructionArgument> arguments;

	@Mock
	private InstructionArgument arg1;

	@Mock
	private InstructionArgument arg2;

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfArguments() {
		when(arguments.size()).thenReturn(1);
		new InstrMove(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		initialize();
		when(arg1.isRegister()).thenReturn(false);// invalid
		new InstrMove(arguments);
	}

	@Test
	public void testDirectDirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x6);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(0x5);
		when(registers.getRegisterValue(5)).thenReturn(15);
		Instruction instr = new InstrMove(arguments);
		instr.execute(computer);
		verify(registers, times(1)).getRegisterValue(5);
		verify(registers, times(1)).setRegisterValue(6, 15);
	}

	@Test
	public void testDirectIndirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(3);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(registers.getRegisterValue(5)).thenReturn(7);
		when(memory.getLocation(10)).thenReturn(4);
		Instruction instr = new InstrMove(arguments);
		instr.execute(computer);
		verify(memory, times(1)).getLocation(10);
		verify(registers, times(1)).getRegisterValue(5);
		verify(registers, times(1)).setRegisterValue(3, 4);
	}

	@Test
	public void testIndirectDirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(3);
		when(registers.getRegisterValue(5)).thenReturn(7);
		when(registers.getRegisterValue(3)).thenReturn(4);
		Instruction instr = new InstrMove(arguments);
		instr.execute(computer);
		verify(memory, times(1)).setLocation(10, 4);
		verify(registers, times(1)).getRegisterValue(5);
		verify(registers, times(1)).getRegisterValue(3);
	}

	@Test
	public void testIndirectNumber() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(arg2.isNumber()).thenReturn(true);
		when(arg2.getValue()).thenReturn(23);
		when(registers.getRegisterValue(5)).thenReturn(7);
		Instruction instr = new InstrMove(arguments);
		instr.execute(computer);
		verify(memory, times(1)).setLocation(10, 23);
		verify(registers, times(1)).getRegisterValue(5);
	}

	@Test
	public void testIndirectIndirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(1 << 24 | ((short) -2) << 8 | 4);
		when(registers.getRegisterValue(5)).thenReturn(7);
		when(registers.getRegisterValue(4)).thenReturn(20);
		when(memory.getLocation(18)).thenReturn(111);
		Instruction instr = new InstrMove(arguments);
		instr.execute(computer);
		verify(memory, times(1)).setLocation(10, 111);
		verify(registers, times(1)).getRegisterValue(5);
		verify(registers, times(1)).getRegisterValue(4);
	}

	private void initialize() {
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(arg1);
		when(arguments.get(1)).thenReturn(arg2);
	}
}
