package hr.fer.zemris.java.tecaj.hw5.db.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.Container;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken.DatabaseTokenType;
import hr.fer.zemris.java.tecaj.hw5.db.operators.DatabaseOperatorFactory;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.Lexer;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryLexer;


/**
 * Represents the indexquerry command of the database. Searches the
 * database and procures all entries that satisfy the given
 * condition.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class IndexQueryCommand implements ICommand {

	/**
	 * Represents this command.
	 */
	private static final String keyword = "indexquery";
	
	/**
	 * Container over which to resolve commands.
	 */
	StudentDatabase database;
	
	
	/**
	 * Creates a command that queries the records in the given
	 * container.
	 * 
	 * @param container container containing database entries
	 */
	public IndexQueryCommand(
			StudentDatabase database) {
		if(database == null){
			throw new IllegalArgumentException("Warning - "
				+ "Cannot query over null!");
		}
		
		this.database = database;
	}
	
	@Override
	public List<StudentRecord> execute(String query) {
		if(query == null){
			System.out.println("Unrecognized input, try again!");
			return null;
		}
		System.out.println("Executing indexquery!");
		
		QueryLexer lexer = new QueryLexer(query);
		
		DatabaseToken token = lexer.nextToken();
		if(token.getType() != DatabaseTokenType.JMBAG){
			throw new Lexer.LexerException("Warning - "
					+ "Unable to parse the given query!");
		}
		
		token = lexer.nextToken();
		if(!token.getValue().equals(DatabaseOperatorFactory.EQUALS)){
			throw new Lexer.LexerException("Warning - "
					+ "Unable to parse the given query!");
		}
		
		token = lexer.nextToken();
		if(token.getType() != DatabaseTokenType.STRING){
			throw new Lexer.LexerException("Warning - "
					+ "Unable to parse the given query!");
		}
		List<StudentRecord> returning = new ArrayList<>();
		returning.add(database.forJMBAG((String)token.getValue()));
		return returning;
	}

	@Override
	public String getKeyword() {
		return keyword;
	}

}
