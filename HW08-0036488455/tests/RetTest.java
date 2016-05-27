import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.*;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Test the InstrRet class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class RetTest {

	@Mock
	Memory memory;
	
	@Mock
	Computer computer;
	
	@Mock 
	Registers registers;
	
	@Mock
	List<InstructionArgument> list;

	@Test
	public void testRet() {
		when(list.size()).thenReturn(0);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(0);
		when(memory.getLocation(1)).thenReturn(0);
		
		InstrRet ret = new InstrRet(list);
		ret.execute(computer);
		
		verify(computer, times(3)).getRegisters();
		verify(computer,times(1)).getMemory();
		verify(registers, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 1);
		verify(memory, times(1)).getLocation(1);
		verify(registers, times(1)).setProgramCounter(0);
	}
}
