package hr.fer.zemris.java.tecaj.hw5.db.conditions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;


/**
 * Composite form of {@link DatabaseCondition}. Houses other 
 * conditions and requires all of them to be fulfilled for it to be
 * fulfilled.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class AggregateCondition extends DatabaseCondition {

	/**
	 * Representatation of ths condition in queries.
	 */
	public static final String REPRESENTATION = "AND";
	
	/**
	 * Children of the condition. All must be fulfilled for the
	 * condition to be fulfilled.
	 */
	List<DatabaseCondition> children;

	
	/**
	 * Creates an AndCondition. Operator is there for leagac support
	 * but not used. Can be set to null.
	 * 
	 */
	public AggregateCondition() {
		super();
		children = new ArrayList<>();
	}

	
	@Override
	public boolean fulfilled(StudentRecord student) {
		boolean isTrue = true;
		for(DatabaseCondition condition : children){
			isTrue = isTrue && condition.fulfilled(student);
		}
		
		return isTrue;
	}

	
	/**
	 * Adds a condition to the condition compostion.
	 * @param condition
	 */
	public void addCondition(DatabaseCondition condition){
		if(condition == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have null condition!");
		}
		
		children.add(condition);
	}
}
