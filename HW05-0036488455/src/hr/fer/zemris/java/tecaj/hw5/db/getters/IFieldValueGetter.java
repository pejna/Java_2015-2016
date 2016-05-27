package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface used to extract data from {@link StudentRecord}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface IFieldValueGetter {

	/**
	 * Extracts the parameter from the given record.
	 * 
	 * @param record record to be disected
	 * @return returns the extracted parameter
	 */
	public String get(StudentRecord record);
}
