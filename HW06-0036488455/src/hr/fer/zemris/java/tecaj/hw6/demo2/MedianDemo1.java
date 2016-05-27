package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;


/**
 * Demonstrates the use of {@link LikeMedian} with {@link Integer} as
 * it's type.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class MedianDemo1 {

	
	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<>();
		System.out.println(likeMedian.get());
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));
		Optional<Integer> result = likeMedian.get();
		System.out.println(result);
		
		for(Integer elem : likeMedian){
			System.out.println(elem);
		}
	}

}
