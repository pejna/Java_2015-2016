package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Strategy used for extracting name from {@link StudentRecord}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class FirstNameGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		if(record == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot extract name from null!");
		}
		return record.getFirstName();
	}

}
