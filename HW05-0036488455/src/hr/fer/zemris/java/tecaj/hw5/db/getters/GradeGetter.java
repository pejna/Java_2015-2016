package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;


/**
 * Extracts the grade as a string from {@link StudentRecord}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class GradeGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		return new Integer(record.getFinalGrade()).toString();
	}

}
