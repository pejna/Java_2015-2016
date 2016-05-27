package hr.fer.zemris.java.hw07.crypto.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw07.crypto.commands.Crypter;


/**
 * Class tests the capabilites of hextobyte method in {@link Crypter} class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class HexToByteTest {

	
	@Test (expected=NullPointerException.class)
	public void testNullInput(){
		
		//will throw
		Crypter.hexToByteArray(null);
	}
	
	@Test
	public void testGoodInput(){
		byte[] array = Crypter.hexToByteArray("d245e7ab00348529ab346765de32ab67");
		byte[] array1 = Crypter.hexToByteArray("d245e7ab00348529ab346765de32ab67");
		
		boolean same = true;
		for(int i = 0 ; i < array.length; i++){
			if(array[i] != array1[i]){
				same = false;
				break;
			}
		}
		
		assertTrue(same);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testShortInput(){
		
		//will throw
		Crypter.hexToByteArray("d5e7ab00348529ab346765de32ab67");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testLongIput(){
	
		//will throw
		Crypter.hexToByteArray("d5e7ab00348352352356463453452529ab346765de32ab67");
	}
	
	@Test
	public void testSlightlyChangedInput(){
		byte[] array = Crypter.hexToByteArray("d245e7ab00348529ab346765de32ab67");
		byte[] array1 = Crypter.hexToByteArray("d345e7ab00348529ab346765de32ab67");
		
		boolean same = true;
		for(int i = 0 ; i < array.length; i++){
			if(array[i] != array1[i]){
				same = false;
				break;
			}
		}
		
		assertFalse(same);
	}
}
