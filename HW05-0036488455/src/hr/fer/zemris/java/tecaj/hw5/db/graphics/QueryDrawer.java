package hr.fer.zemris.java.tecaj.hw5.db.graphics;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.getters.FirstNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.JmbagGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.LastNameGetter;


/**
 * Used for formated printing of {@link StudentRecord} to desired
 * {@link PrintStream}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class QueryDrawer {

	/**
	 * Each column has a record value getter.
	 */
	private IFieldValueGetter[] column;
	
	/**
	 * Each column has a width of longest member.
	 */
	private int[] columnWidth;
	
	/**
	 * Output to which to print the records.
	 */
	private PrintStream output;
	
	/**
	 * Space left between the value and the line splitter.
	 */
	private int columnSpace;
	
	/**
	 * Counts the number of records taken.
	 */
	private int lineCounter;
	
	/**
	 * Statement with which to finish the printing.
	 */
	private String closingStatement;
	
	/**
	 * Default output.
	 */
	private static final PrintStream DEFAULT_PRINT_STREAM = 
			System.out;
	
	/**
	 * Used for line crosssection.
	 */
	private static final char CROSS_SECTION = '+';
	
	/**
	 * Used for drawing horizontal line.
	 */
	private static final char HORIZONTAL_LINE = '=';
	
	/**
	 * Used for drawing vertical line.
	 */
	private static final char VERTICAL_LINE = '|';
	
	/**
	 * Default number of spaces between attribute and line splitter.
	 */
	private static final int DEFAULT_COLUMN_SPACE = 1;
	
	/**
	 * Used for drawing space.
	 */
	private static final char SPACE = ' ';
	
	/**
	 * Form of the final string.
	 */
	private static final String DEFAULT_CLOSING_STATEMENT = 
			"Records selected: ";
	
	
	/**
	 * Creates a {@link QueryDrawer} that formats the output with
	 * the columns selected by given {@link IFieldValueGetter}.
	 * If given none dows not write any columns. If given null
	 * throws {@link IllegalArgumentException}.
	 * 
	 * @param columns any number of columns, each
	 * {@link IFieldValueGetter} will denote 1 column in the order
	 * given to the constructor
	 */
	public QueryDrawer(IFieldValueGetter ... columns) {
		if(columns == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have null amount of columns!");
		}
		
		column = new IFieldValueGetter[columns.length];
		for(int i = 0; i < columns.length; i++){
			column[i] = columns[i];
		}
		
		columnWidth = new int[columns.length];
		output = DEFAULT_PRINT_STREAM;
		columnSpace = DEFAULT_COLUMN_SPACE;
		lineCounter = 0;
		closingStatement = DEFAULT_CLOSING_STATEMENT;
	}
	
	
	/*
	 * ******** Setter methods ***************************************
	 */
	
	
	/**
	 * Sets the given stream as the output.
	 * 
	 * @param stream stream to be written to
	 */
	public void setOutputStream(PrintStream stream){
		if(stream == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot print to null!");
		}
		
		output = stream;
	}
	
	
	/**
	 * Sets the given message as the drwawer closing statement.
	 * 
	 * @param message message to be printed at the end
	 */
	public void setClosingMessage(String message){
		if(message == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot close operation with null!");
		}
		
		closingStatement = message;
	}
	
	
	/**
	 * Sets a new value as the spacing. Throws 
	 * {@link IllegalArgumentException} if given less than 0.
	 * 
	 * @param number number of spaces to print
	 */
	public void setSpacing(int number){
		if(number < 0){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have less than 0 spaces!");
		}
		
		columnSpace = number;
	}
	/*
	 * ******** Drawer methods ***************************************
	 */
	
	
	/**
	 * Prints the given list in the predetermined format.
	 * 
	 * @param students list of students to be printed
	 */
	public void print(List<StudentRecord> students){
		if(students == null){
			return;
		}
		
		determineColumnWidth(students);
		
		if(lineCounter != 0){
			printHorizontalMargin();
			printLines(students);
			printHorizontalMargin();
		}
		
		printResult();
	}
	
	
	/*
	 * ******** Print helper methods *********************************
	 */
	
	
	/**
	 * Determined the width of each of the columns.
	 * 
	 * @param students list of students on which to determine
	 * the width
	 */
	private void determineColumnWidth(List<StudentRecord> students){
		for(StudentRecord student : students){
			if(student == null){
				continue;
			}
			
			for(int i = 0; i < column.length; i++){
				if(column[i].get(student).length() > columnWidth[i]){
					columnWidth[i] = column[i].get(student).length();
				}
			}
			lineCounter++;
		}
	}
	
	
	/**
	 * Prints a horizontal margin.
	 */
	private void printHorizontalMargin(){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < columnSpace; i++){
			sb.append(SPACE);
		}
		
		sb.append(CROSS_SECTION);
		
		for(int i = 0; i< column.length; i++){
			for(int j = 0; j < 2 * columnSpace + columnWidth[i]; j++){
				sb.append(HORIZONTAL_LINE);
			}
			
			sb.append(CROSS_SECTION);
		}
		
		for(int i = 0; i < columnSpace; i++){
			sb.append(SPACE);
		}
		
		output.println(sb.toString());
	}
	
	
	/**
	 * Prints the body of the list.
	 * 
	 * @param students students to be printed
	 */
	private void printLines(List<StudentRecord> students){
		for(StudentRecord student : students){
			printLine(student);
		}
	}
	
	
	/**
	 * Prints a line containing information on the given student.
	 * 
	 * @param student student containing the information
	 */
	private void printLine(StudentRecord student){
		StringBuilder sb = new StringBuilder();
		sb.append(lineSplit());
		for(int i = 0; i < column.length; i++){
			String attribute = column[i].get(student);
			sb.append(attribute);
			for(int j = 0; 
					j < columnWidth[i] - attribute.length(); j++){
				sb.append(SPACE);
			}
			sb.append(lineSplit());
		}
		output.println(sb.toString());
	}
	
	
	/**
	 * Creates a line splitter containing a vertical line and
	 * predetermined number of spaces.
	 * 
	 * @return returns the line splitting string
	 */
	private String lineSplit(){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < columnSpace; i++){
			sb.append(SPACE);
		}
		
		sb.append(VERTICAL_LINE);
		
		for(int i = 0 ; i < columnSpace; i++){
			sb.append(SPACE);
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Prints the closing statement of the drawer saying the number
	 * of students found.
	 * 
	 */
	private void printResult(){
		StringBuilder sb = new StringBuilder();
		sb.append(closingStatement);
		sb.append(lineCounter);
		output.println(sb.toString());
	}
	
	
	/*
	 * ******** Demonstration methods ********************************
	 */
	

	/**
	 * Demonstrates the use of {@link QueryDrawer}. Not meant to be
	 * used otherwise.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		QueryDrawer drawer = new QueryDrawer(new FirstNameGetter(),
				 new JmbagGetter(), 
				 new LastNameGetter(), new JmbagGetter(),new JmbagGetter(),new JmbagGetter(),new JmbagGetter(),new JmbagGetter(), 
				new LastNameGetter());
		
		List<StudentRecord> list = new ArrayList<>();
		list.add(new StudentRecord("0033\tAnte\tIvica\t4"));
		list.add(new StudentRecord("0042\tMirangula\tAna\t4"));
		
		drawer.print(list);
	}
}
