package hr.fer.zemris.java.tecaj.hw5.db.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.Container;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.filter.QueryFilter;


/**
 * Represents the querry command of the database. Searches the
 * database and procures all entries that satisfy the given
 * condition.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class QueryCommand implements ICommand {

	/**
	 * Represents this command.
	 */
	private static final String keyword = "query";
	
	/**
	 * Container over which to resolve commands.
	 */
	StudentDatabase database;
	
	
	/**
	 * Creates a command that resolves queries on the given container.
	 * 
	 * @param container container for the records
	 */
	public QueryCommand(StudentDatabase database) {
		this.database = database;
	}
	
	
	@Override
	public List<StudentRecord> execute(String query) {
		System.out.println("Executing query!");
		QueryFilter filter = new QueryFilter(query);
		return database.filter(filter);
	}


	@Override
	public String getKeyword() {
		return keyword;
	}

}
