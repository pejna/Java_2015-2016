package hr.fer.zemris.java.tecaj.hw6.demo2;


/**
 * Demonstrates the use of {@link LikeMedian} with {@link String} as
 * it's type.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class MedianDemo2 {

	
	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<>();
		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");
		String result = likeMedian.get().get().toString();
		
		System.out.println(result);
	}
}
