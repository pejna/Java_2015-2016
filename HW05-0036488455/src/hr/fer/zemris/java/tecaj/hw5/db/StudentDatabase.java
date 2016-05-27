package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.filter.IFilter;


/**
 * Database containing {@link StudentRecord}. Can be accesed 
 * individually or as a group through querries.
 * 
 * @author Juraj Pejnovic
 * @version 2.0
 */
public class StudentDatabase {

	/**
	 * Container of {@link StudentRecord}s
	 */
	private Container<String, StudentRecord> container;
	
	
	/**
	 * Creates a database and fills it with the given lines.
	 * 
	 * @param lines linec sontaining {@link StudentRecord}
	 */
	public StudentDatabase(List<String> lines){
		if(lines == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot fill database with null!");
		}
		
		container = new DatabaseContainer();
		for(String line : lines){
			container.addItem(new StudentRecord(line));
		}
		

	}

	
	/**
	 * Gets the {@link StudentRecord} with the provided jmbag.
	 * 
	 * @param jmbag jmbag of the student 
	 * @return returns the matching record
	 */
	public StudentRecord forJMBAG(String jmbag){
		return (StudentRecord) container.getItem(jmbag);
	}
	
	
	/**
	 * Gets the list of records accepted by the given filter.
	 * 
	 * @param filter filter for filtering records
	 * @return returns the list of accepted records
	 */
	public List<StudentRecord> filter(IFilter<StudentRecord> filter){
		List<StudentRecord> accepted = new ArrayList<>();
		for(StudentRecord record : container){
			if(filter.accepts(record)){
				accepted.add(record);
			}
		}
		
		return accepted;
	}

}
