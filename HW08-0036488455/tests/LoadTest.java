import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Class tests good execution of InstrLoad.
 * 
 * @author Juraj Pejnovic
 * @version 0.5
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadTest {
	
	@Mock
	Memory memory;
	
	@Mock
	Computer computer;
	
	@Mock 
	Registers registers;
	
	@Mock
	List<InstructionArgument> list;
	
	InstructionArgument arg1 = new InstructionArgumentImpl(true, 0x00000000);
	InstructionArgument	arg2 = new InstructionArgumentImpl(false, 0);
	
	@Test
	public void testLoad(){
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(memory.getLocation(0)).thenReturn(0);
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(arg1);
		when(list.get(1)).thenReturn(arg2);
		
		InstrLoad instr = new InstrLoad(list);
		instr.execute(computer);
		
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).getLocation(0);
		verify(computer, times(1)).getRegisters();
		verify(registers, times(1)).setRegisterValue(0, 0);
		
	}
	
	private static class InstructionArgumentImpl implements InstructionArgument{

		private int value;
		private boolean register;
		
		public InstructionArgumentImpl(boolean register, int value) {
			this.value = value;
			this.register = register;
		}
		
		@Override
		public boolean isRegister() {
			return register;
		}

		@Override
		public boolean isString() {
			return false;
		}

		@Override
		public boolean isNumber() {
			return !register;
		}

		@Override
		public Object getValue() {
			return value;
		}
		
	}
	
}
