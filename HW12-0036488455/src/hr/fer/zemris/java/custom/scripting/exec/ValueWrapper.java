package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;


/**
 * Container of various types of objects. Enables arithmetic
 * operations on the objects. Accepted types of objects are 
 * {@link Integer}, {@link String} and {@link Double}. If it 
 * contains {@link String} that is not a number, arithmetic 
 * operations cannot be performed and will throw {@link Exception}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ObjectMultistack
 */
public class ValueWrapper {

	/**
	 * Value contained.
	 */
	private Object value;

	/**
	 * Interpretation of null for operations if found in objects.
	 */
	private static final int NULL_VALUE = 0;
	
	/**
	 * Letters contained in {@link String} that cound mean the
	 * {@link String} represents a {@link Double}.
	 */
	private static final String[] DOUBLE_RECOGNIZERS = {
			"E",
			"e",
			"."
	};

	/**
	 * Represents mathematical zero.
	 */
	private static final Double ZERO = (double) 0;
	
	
	/**
	 * Creates a {@link ValueWrapper} that wraps the given value.
	 * @param value 
	 * @throws IllegalArgumentException thrown if value is not
	 * of accepted type
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	
	/*
	 * ******** Algebra methods **************************************
	 */
	
	
	/**
	 * Increments the value contained by the amount contained
	 * in the given object. If given a {@link Double} or a 
	 * {@link String} containing {@link Double} will cast currently
	 * contained value to {@link Double} if not will cast currently
	 * contained value to {@link Integer}.
	 * 
	 * @param incValue value we want the current value to be 
	 * incremented by
	 */
	public void increment(Object incValue){
		checkAcceptedType(incValue);
		checkAcceptedType(value);
		
    	calculate(value, incValue, (s, t) -> s + t);
	}
	
	
	/**
	 * Decrements the value contained by the amount contained
	 * in the given object. If given a {@link Double} or a 
	 *  {@link String} containing {@link Double} will cast currently
	 *  contained value to {@link Double} if not will cast currently
	 * contained value to {@link Integer}.
	 * 
	 * @param decValue value we want the current value to be 
	 * decremented by
	 */
	public void decrement(Object decValue){
		checkAcceptedType(decValue);
		checkAcceptedType(value);
		
    	calculate(value, decValue, (s, t) -> s - t);
	}
	
	
	/**
	 * Multiplies the value contained with the amount contained
	 * in the given object. If given a {@link Double} or a 
	 *  {@link String} containing {@link Double} will cast currently
	 *  contained value to {@link Double} if not will cast currently
	 * contained value to {@link Integer}.
	 * 
	 * @param mulValue value we want the current value to be 
	 * multiplied with
	 */
	public void multiply(Object mulValue){
		checkAcceptedType(mulValue);
		checkAcceptedType(value);
		
    	calculate(value, mulValue, (s, t) -> s * t);
	}
	
	
	/**
	 * Divides the value contained by the amount contained
	 * in the given object. If given a {@link Double} or a 
	 *  {@link String} containing {@link Double} will cast currently
	 *  contained value to {@link Double} if not will cast currently
	 * contained value to {@link Integer}.
	 * 
	 * @param divValue value we want the current value to be 
	 * divided by
	 */
	public void divide(Object divValue){
		checkAcceptedType(divValue);
		checkAcceptedType(value);
		if(toDouble(divValue).equals(ZERO)){
			throw new RuntimeException("Warning - "
					+ "Cannot divide by zero!");
		}
		
    	calculate(value, divValue, (s, t) -> s / t);
	}
	
	
	/**
	 * Compares the given object with value stored. Accepts only
	 * accepted types. 
	 * 
	 * @param withValue value compared to
	 * @return returns negative number if given object has greater
	 * value, 0 if given object is of the same value, or positive
	 * if the given object value is lesser than value stored
	 */
	public int numCompare(Object withValue){
		checkAcceptedType(withValue);
		
		double doubleValue = toDouble(value);
		double comparedValue = toDouble(withValue);
		
		return Double.compare(doubleValue, comparedValue);
		
	}
	
	
	/*
	 * ******** Getters and setters **********************************
	 */
	
	
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	
	/*
	 * ******** Utility methods **************************************
	 */
	
	
	/**
	 * Checks if the given object is of the type allowed.
	 * 
	 * @param o object to be checked
	 * @return returns true if accepted, otherwise false
	 */
	private void checkAcceptedType(Object o){
		if(o == null){
			return;
		}
		
		if(o.getClass().equals(Integer.class)){
			return;
		}
		
		if(o.getClass().equals(Double.class)){
			return;
		}
		
		if(o.getClass().equals(String.class)){
			return;
		}
		
		throw new IllegalArgumentException("Warning - "
					+ "Object is not of accepted type!"
					+ "Got type: " + o.getClass());
	}
	
	
	/**
	 * Converts the given object of accepted type to Double.
	 * 
	 * @param argument object to be converted
	 * @return returns the value of given object as Double
	 */
	private Double toDouble(Object argument){
		if(argument == null){
			return new Double(NULL_VALUE);
			
		} 
		
		if(argument.getClass().equals(String.class)){
			return Double.parseDouble((String)argument);
			
		}
		
		if(argument.getClass().equals(Double.class)){
			return ((Double)argument).doubleValue();
			
		}
		
		return ((Integer) argument).doubleValue();
		
	}
	
	
	/**
	 * Checks if any of the two given objects is actually a 
	 * {@link Double}.
	 * 
	 * @param o1 one of the objects checked
	 * @param o2 one of the objects checked
	 * @return returns true if any is {@link Double}, otherwise false
	 */
	private boolean checkHasDouble(Object o1, Object o2){
		if(checkObjectDouble(o1) || checkObjectDouble(o2)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks if the object is {@link Double}.
	 * @param object object to be checked
	 * @return returns true if is {@link Double}, otherwise false
	 */
	private boolean checkObjectDouble(Object object){
		if(object == null){
			return false;
		}
		
		if(object.getClass().equals(Double.class)){
			return true;
		}
		
		if(object.getClass().equals(String.class)){
			String string = object.toString();
			for(String sequence : DOUBLE_RECOGNIZERS){
				if(string.contains(sequence)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Calculates the given operation and saves the value calculated
	 * in the value contained in this object.
	 * 
	 * @param v1 first argument of the calculation
	 * @param v2 second argument of the calculation
	 * @param function calculation to be colaculated on the given
	 * objects
	 */
	private void calculate(Object v1, Object v2, 
			BiFunction<Double, Double, Object> function){
		
		if(checkHasDouble(v1, v2)){
			value = function.apply(toDouble(v1), toDouble(v2));
			
		} else {
			Double result = (Double) function
					.apply(toDouble(v1), toDouble(v2));
			value = new Integer(result.intValue());
		}
	}
}
