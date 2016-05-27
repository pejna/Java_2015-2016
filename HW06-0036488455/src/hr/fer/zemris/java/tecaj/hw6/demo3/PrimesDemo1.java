package hr.fer.zemris.java.tecaj.hw6.demo3;


/**
 * Test the iteration upon {@link PrimesCollection} class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class PrimesDemo1 {

	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		
		for(Integer prime : primesCollection){
			System.out.println("Got prime: " + prime);
		}
	}
}
