package hr.fer.zemris.java.tecaj.hw5.db.operators;

import java.text.Collator;
import java.util.Locale;

/**
 * Determines whether the first given value is lexicographically
 * lesser of equal to the second given value.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class LessOrEqualsComparisonOperator 
			implements IComparisonOperator {

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
		Collator collator = 
				Collator.getInstance(new Locale("hr", "HR"));
	
		return collator.compare(value1, value2) <= 0;
	}

}
