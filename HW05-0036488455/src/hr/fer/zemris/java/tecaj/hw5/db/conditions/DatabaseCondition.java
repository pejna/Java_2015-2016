package hr.fer.zemris.java.tecaj.hw5.db.conditions;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * Top conditioner for all Database conditions.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public abstract class DatabaseCondition
		implements IConditionalExpression<StudentRecord> {

	
	/**
	 * Operator of the condition.
	 */
	protected IComparisonOperator operator;
	
	/**
	 * Right side of the comparison.
	 */
	protected String comparedTo;
	
	
	/**
	 * Constructor used in conditions where operator is not used.
	 */
	public DatabaseCondition(){
		this.operator = null;
	}
	
	
	/**
	 * Creates a database condition with the given operator as a
	 * condition operator.
	 * 
	 * @param operator operator that defines the condition
	 */
	public DatabaseCondition(IComparisonOperator operator) {
		if(operator == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have null operator!");
		}
		
		this.operator = operator;
	}
	
	
	/**
	 * Adds the right side value of the condition.
	 */
	public void addComparedTo(String string){
		if(string == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have null tocompare to!");
		}
		
		comparedTo = string;
	}
	
	@Override
	public abstract boolean fulfilled(StudentRecord object);
	
}
