package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Collection of prime numbers that allows iteration upon them.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer>{

	/**
	 * Number of prime numbers the collection is able to iterate 
	 * upon.
	 */
	private int size;
	
	
	/**
	 * Creates the collection with the given size.
	 * 
	 * @param size size of the collection
	 */
	public PrimesCollection(int size){
		if(size <= 0){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have size lesser than 1!");
		}
		
		this.size = size;
	}

	
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}
	
	
	
	/**
	 * Iterator implementation that iterates upon this class.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private class PrimesIterator implements Iterator<Integer>{

		/**
		 * Current position in the collection.
		 */
		private int currentPosition = 0;
		
		/**
		 * Current prime number found.
		 */
		private int currentNumber = 2;
		
		/**
		 * Is this the first call of the iterator.
		 */
		private boolean isFirst = true;
		
		
		@Override
		public boolean hasNext() {
			if(currentPosition < size){
				return true;
			}
			
			return false;
		}

		
		@Override
		public Integer next() {
			if(!hasNext()){
				throw new NoSuchElementException("Warning - "
						+ "No more elements to iterate!");
			}
			
			if(isFirst){
				isFirst = false;
				currentPosition++;
				return currentNumber;
			}
			
			int newPrime = currentNumber + 1;
			while(!isPrime(newPrime)){
				newPrime++;
			}
			
			currentPosition++;
			currentNumber = newPrime;
			return currentNumber;
		}
	}

	
	/**
	 * Checks if the given number is a prime number.
	 * 
	 * @param n number to be checked
	 * @return returns true if prime, otherwise returns false
	 */
	private boolean isPrime(int n){
		if(n % 2 == 0){
			return false;
		}
		
		for(int i = 3; i*i <= n; i+=2){
			if(n % i == 0){
				return false;
			}
		}
		
		return true;
	}
}
