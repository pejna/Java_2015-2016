package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.Hashtable;
import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * Container of {@link StudentRecord}s for the {@link StudentDatabase}
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class DatabaseContainer 
		implements Container<String, StudentRecord> {

	/**
	 * List containing the records in order of adding.
	 */
	List<StudentRecord> list;
	
	/**
	 * Map containing the records indexed by ther jmbags
	 */
	Hashtable<String, StudentRecord> table;
	
	
	/**
	 * Creates a normal database container.
	 */
	public DatabaseContainer() {
		list = new ArrayList<>();
		table = new SimpleHashtable<>();
	}
	
	
	/*
	 * ******** Putter methods ***************************************
	 */
	

	/**
	 * Adds an item into the container.
	 * 
	 * @param record item to be added
	 */
	@Override
	public void addItem(Object record){
		if(record == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot put null inside container!");
		}
		if(!record.getClass().equals(StudentRecord.class)){
			throw new IllegalArgumentException("Warning - "
					+ "Given object is not of StudentRecord class!");
		}
		
		
		list.add((StudentRecord)record);
		table.put(((StudentRecord)record).getJmbag(), 
				(StudentRecord)record);
	}
	
	/*
	 * ******** Getter methods ***************************************
	 */
	
	@Override
	public List<StudentRecord> getItemList() {
		return list;
	}

	@Override
	public StudentRecord getItem(String key) {
		return table.get(key);
	}

	@Override
	public Iterator<StudentRecord> iterator() {
		return list.iterator();
	}
}
