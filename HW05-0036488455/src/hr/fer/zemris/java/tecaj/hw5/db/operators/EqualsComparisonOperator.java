package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Determines whether the given strings are equal.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class EqualsComparisonOperator implements IComparisonOperator{


	@Override
	public boolean satisfied(String value1, String value2) {
		if(value1 == null){
			throw new IllegalArgumentException("Warning - "
					+ "Value1 cannot be null!");
		}
		if(value2 == null){
			throw new IllegalArgumentException("Warning - "
					+ "Value 2 cannot be null!");
		}
		
		return value1.equals(value2);
	}

}
