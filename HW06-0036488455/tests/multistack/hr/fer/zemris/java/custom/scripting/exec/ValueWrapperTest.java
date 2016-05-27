package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the methods of {@link ValueWrapper} class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ValueWrapperTest {

	@Test
	public void testIntIntIncrement(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.increment(new Integer(5));
		assertEquals(new Integer(15), wrapper.getValue());
	}
	
	@Test
	public void testIntDoubleIncrement(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.increment(new Double(5));
		assertEquals(new Double(15), wrapper.getValue());
	}
	
	@Test
	public void testIntStringIncrement(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.increment("5");
		assertEquals(new Integer(15), wrapper.getValue());
	}
	
	@Test
	public void testDoubleDoubleIncrement(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.increment(new Double(5));
		assertEquals(new Double(15), wrapper.getValue());
	}
	
	@Test
	public void testDoubleStringIncrement(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.increment("5");
		assertEquals(new Double(15), wrapper.getValue());
	}
	
	@Test
	public void testStringStringIncrement(){
		ValueWrapper wrapper = new ValueWrapper("10");
		wrapper.increment("5");
		assertEquals(new Integer(15), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongStringIncrement(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.increment("5");
		assertEquals(new Integer(15), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongIntIncrement(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.increment(new Integer(5));
		assertEquals(new Integer(15), wrapper.getValue());
	}
	

	@Test (expected=RuntimeException.class)
	public void testWrongDoubleIncrement(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.increment("5");
		assertEquals(new Integer(15), wrapper.getValue());
	}
	
	@Test
	public void testNullNullIncrement(){
		ValueWrapper wrapper = new ValueWrapper(null);
		
		//will throw
		wrapper.increment(null);
		assertEquals(new Integer(0), wrapper.getValue());
	}
	
	/*
	 * ***************************************************************
	 */

	@Test
	public void testIntIntDecrement(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.decrement(new Integer(5));
		assertEquals(new Integer(5), wrapper.getValue());
	}
	
	@Test
	public void testIntDoubleDecrement(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.decrement(new Double(5));
		assertEquals(new Double(5), wrapper.getValue());
	}
	
	@Test
	public void testIntStringDecrement(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.decrement("5");
		assertEquals(new Integer(5), wrapper.getValue());
	}
	
	@Test
	public void testDoubleDoubleDecrement(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.decrement(new Double(5));
		assertEquals(new Double(5), wrapper.getValue());
	}
	
	@Test
	public void testDoubleStringDecrement(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.decrement("5");
		assertEquals(new Double(5), wrapper.getValue());
	}
	
	@Test
	public void testStringStringDecrement(){
		ValueWrapper wrapper = new ValueWrapper("10");
		wrapper.decrement("5");
		assertEquals(new Integer(5), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongStringDecrement(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.decrement("5");
		assertEquals(new Integer(5), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongIntDecrement(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.decrement(new Integer(5));
		assertEquals(new Integer(5), wrapper.getValue());
	}
	

	@Test (expected=RuntimeException.class)
	public void testWrongDoubleDecrement(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.decrement("5");
		assertEquals(new Integer(5), wrapper.getValue());
	}
	
	@Test
	public void testNullNullDecrement(){
		ValueWrapper wrapper = new ValueWrapper(null);
		
		wrapper.decrement(null);
		assertEquals(new Integer(0), wrapper.getValue());
	}
	
	/*
	 * ***************************************************************
	 */

	@Test
	public void testIntIntMultiply(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.multiply(new Integer(5));
		assertEquals(new Integer(50), wrapper.getValue());
	}
	
	@Test
	public void testIntDoubleMultiply(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.multiply(new Double(5));
		assertEquals(new Double(50), wrapper.getValue());
	}
	
	@Test
	public void testIntStringMultiply(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.multiply("5");
		assertEquals(new Integer(50), wrapper.getValue());
	}
	
	@Test
	public void testDoubleDoubleMultiply(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.multiply(new Double(5));
		assertEquals(new Double(50), wrapper.getValue());
	}
	
	@Test
	public void testDoubleStringMultiply(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.multiply("5");
		assertEquals(new Double(50), wrapper.getValue());
	}
	
	@Test
	public void testStringStringMultiply(){
		ValueWrapper wrapper = new ValueWrapper("10");
		wrapper.multiply("5");
		assertEquals(new Integer(50), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongStringMultiply(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.multiply("5");
		assertEquals(new Integer(50), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongIntMultiply(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.multiply(new Integer(5));
		assertEquals(new Integer(50), wrapper.getValue());
	}
	

	@Test (expected=RuntimeException.class)
	public void testWrongDoubleMultiply(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.multiply("5");
		assertEquals(new Integer(50), wrapper.getValue());
	}
	
	@Test
	public void testNullNullMultiply(){
		ValueWrapper wrapper = new ValueWrapper(null);
		
		wrapper.increment(null);
		assertEquals(new Integer(0), wrapper.getValue());
	}
	
	/*
	 * ***************************************************************
	 */
	

	@Test
	public void testIntIntDivide(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.divide(new Integer(5));
		assertEquals(new Integer(2), wrapper.getValue());
	}
	
	@Test
	public void testIntDoubleDivide(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.divide(new Double(5));
		assertEquals(new Double(2), wrapper.getValue());
	}
	
	@Test
	public void testIntStringDivide(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		wrapper.divide("5");
		assertEquals(new Integer(2), wrapper.getValue());
	}
	
	@Test
	public void testDoubleDoubleDivide(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.divide(new Double(5));
		assertEquals(new Double(2), wrapper.getValue());
	}
	
	@Test
	public void testDoubleStringDivide(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		wrapper.divide("5");
		assertEquals(new Double(2), wrapper.getValue());
	}
	
	@Test
	public void testStringStringDivide(){
		ValueWrapper wrapper = new ValueWrapper("10");
		wrapper.divide("5");
		assertEquals(new Integer(2), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongStringDivide(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.divide("5");
		assertEquals(new Integer(2), wrapper.getValue());
	}

	@Test (expected=RuntimeException.class)
	public void testWrongIntDivide(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.divide(new Integer(5));
		assertEquals(new Integer(2), wrapper.getValue());
	}
	

	@Test (expected=RuntimeException.class)
	public void testWrongDoubleDivide(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		wrapper.divide("5");
		assertEquals(new Integer(2), wrapper.getValue());
	}
	
	@Test (expected=RuntimeException.class)
	public void testNullNullDivide(){
		ValueWrapper wrapper = new ValueWrapper(null);
		
		//will throw
		wrapper.divide(null);
		assertEquals(new Integer(0), wrapper.getValue());
	}
	
	/*
	 * ***************************************************************
	 */

	@Test
	public void testIntIntCompare(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		assertTrue( wrapper.numCompare(new Integer(5)) > 0);
	}
	
	@Test
	public void testIntDoubleCompare(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		assertTrue( wrapper.numCompare(new Double(5)) > 0);
	}
	
	@Test
	public void testIntStringCompare(){
		ValueWrapper wrapper = new ValueWrapper(new Integer(10));
		assertTrue( wrapper.numCompare("5") > 0);
	}
	
	@Test
	public void testDoubleDoubleCompare(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		assertTrue( wrapper.numCompare(new Double(5)) > 0);
	}
	
	@Test
	public void testDoubleStringCompare(){
		ValueWrapper wrapper = new ValueWrapper(new Double(10));
		assertTrue( wrapper.numCompare("5") > 0);
	}
	
	@Test
	public void testStringStringCompare(){
		ValueWrapper wrapper = new ValueWrapper("10");
		assertTrue( wrapper.numCompare("5") > 0);
	}

	@Test (expected=RuntimeException.class)
	public void testWrongStringCompare(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		assertTrue( wrapper.numCompare("5") > 0);
	}

	@Test (expected=RuntimeException.class)
	public void testWrongIntCompare(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		assertTrue( wrapper.numCompare(new Integer(5)) > 0);
	}
	

	@Test (expected=RuntimeException.class)
	public void testWrongDoubleCompare(){
		ValueWrapper wrapper = new ValueWrapper("Baka");
		
		//will throw
		assertTrue( wrapper.numCompare(new Double(5)) > 0);
	}
	
	@Test
	public void testNullNullCompare(){
		ValueWrapper wrapper = new ValueWrapper(null);
		
		assertTrue( wrapper.numCompare(null) == 0);
	}
}
