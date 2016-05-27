package hr.fer.zemris.java.tecaj.hw6.demo3;


/**
 * Test nested iteration upon {@link PrimesCollection} class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class PrimesDemo2 {

	
	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection){
			for(Integer prime2 : primesCollection){
				System.out.println("Got prime pair: " + prime 
						+ ", " + prime2);
			}
		}
	}
}
