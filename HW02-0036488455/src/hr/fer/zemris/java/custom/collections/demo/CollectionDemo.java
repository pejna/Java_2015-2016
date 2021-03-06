package hr.fer.zemris.java.custom.collections.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.
			LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

/**
 * Used for demonstrating classes from 
 * hr.fer.zemris.java.custom.collections. 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class CollectionDemo {

	/**
	 * Starts the execution of the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		System.out.println(Arrays.toString(col.toArray()));

		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); 
		// here the internal array is reallocated to 4
		
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1);
		// removes "New York"; shifts "San Francisco" to position 1
		
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		
		try{
			col.add(null);
		} catch (IllegalArgumentException exeception){
//			System.out.println("Predo null!");
		}
		LinkedListIndexedCollection col2 = new 
				LinkedListIndexedCollection(col);
		
		System.out.println("col1 elements:");
		col.forEach(new Processor(){
			public void process(Object o) {
				System.out.println(o);
			}
		});
		
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		
		System.out.println("col2 elements:");
		col2.forEach(new Processor(){
			public void process(Object o) {
				System.out.println(o);
			}
		});
		
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		
		col.remove(new Integer(20));
		// removes 20 from collection (at position 0).
		
		
	}

}
