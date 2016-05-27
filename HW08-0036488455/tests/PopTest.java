import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class PopTest {


	@Mock
	Memory memory;
	
	@Mock
	Computer computer;
	
	@Mock 
	Registers registers;
	
	@Mock
	List<InstructionArgument> list;

	@Mock
	InstructionArgument arg;

	@Test
	public void testPop() {
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(arg);
		
		when(arg.isRegister()).thenReturn(true);
		when(arg.getValue()).thenReturn(0);
		
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(0);
		when(memory.getLocation(1)).thenReturn(0);
		
		InstrPop ret = new InstrPop(list);
		ret.execute(computer);
		
		verify(computer, times(3)).getRegisters();
		verify(computer,times(1)).getMemory();
		verify(registers, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 1);
		verify(memory, times(1)).getLocation(1);
		verify(registers, times(1)).setRegisterValue(0, 0);
	}
}
