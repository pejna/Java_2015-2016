package hr.fer.zemris.java.tecaj.hw5.db.filter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.conditions.DatabaseConditionFactory;
import hr.fer.zemris.java.tecaj.hw5.db.conditions.IConditionalExpression;

/**
 * Filters the {@link StudentRecord} with the conditions
 * given in the constructor.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class QueryFilter implements IFilter<StudentRecord>{

	/**
	 * Condition for the filter to accept the given item.
	 */
	IConditionalExpression<StudentRecord> condition;
	
	
	/**
	 * Creates a filter with the given condition for acceptance.
	 * 
	 * @param condition condition of the querry
	 */
	public QueryFilter(String condition) {
		if(condition == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have null query!");
		}
		this.condition = DatabaseConditionFactory.produce(condition);
	}
	
	@Override
	public boolean accepts(StudentRecord item) {
		if(item == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot determine acceptance of null!");
		}
		
		return condition.fulfilled(item);
	}

}
