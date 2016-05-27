package hr.fer.zemris.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DemoTest2 {
	@Mock
	private List<Integer> list;

	@Test
	public void test1() {
		when(list.get(0)).thenReturn(5);
		when(list.get(1)).thenReturn(6);
		int rez = metoda(list);
		verify(list, times(1)).get(0);
		verify(list, times(2)).get(1);
		verify(list, times(1)).set(3, 17);
		assertEquals(17, rez);
	}

	public int metoda(List<Integer> list) {
		int suma = list.get(0) + list.get(1) + list.get(1);
		list.set(3, suma);
		return suma;
	}
}
