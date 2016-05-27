package hr.fer.zemris.java.tecaj.hw5.db.conditions;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * Database condition that checks the desired parameter of the given
 * student record.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class OperatorCondition extends DatabaseCondition {

	IFieldValueGetter getter;
	
	
	/**
	 * Creates a condition with the given operator that exctracts
	 * the desired parameter denoted by the getter.
	 * 
	 * @param getter getter of parameter from {@link StudentRecord}
	 * @param comparedTo value that the parameter is compared to
	 * @param operator operator that is used in comparison
	 */
	public OperatorCondition(IFieldValueGetter getter, 
			String comparedTo, IComparisonOperator operator) {
		super(operator);
		
		if(comparedTo == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot compare to null!");
		}
		if(getter == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot get values with null!");
		}
		
		addComparedTo(comparedTo);
		this.getter = getter;
		
	}

	
	@Override
	public boolean fulfilled(StudentRecord student) {
		return operator.satisfied(getter.get(student), comparedTo);
	}

}
