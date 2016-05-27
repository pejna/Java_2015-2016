package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


/**
 * Accumulates objects ang calculates their median value.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @param <T> comparable object type whose median we want found
 */
public class LikeMedian<T extends Comparable<T>> 
implements Iterable<T>{
	
	/**
	 * List of elements.
	 */
	private List<T> elements;
	
	
	/**
	 * Creates the median calculator.
	 */
	public LikeMedian() {
		elements = new ArrayList<>();
	}
	
	
	/**
	 * Adds the given value to the median calculatin group.
	 * 
	 * @param value
	 */
	public void add(T value){
		elements.add(value);
	}
	
	
	/**
	 * Calculates the median of the values added into this object.
	 * 
	 * @return returns the median if found, if not returns an 
	 * {@link Optional} containing no values
	 */
	public Optional<T> get(){
		if(elements.size() == 0){
			return Optional.empty();
		}
		List<T> tempList = new ArrayList<>(elements);
		elements.sort(T::compareTo);
		return Optional.of(tempList.get((tempList.size() - 1)/2));
	}

	
	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}
}
