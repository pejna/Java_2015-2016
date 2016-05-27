package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Represents a record of a single student.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class StudentRecord {
	
	/**
	 * Jmbag of the student.
	 */
	private String jmbag;
	
	/**
	 * Surname of the student.
	 */
	private String surname;
	
	/**
	 * Name of the student.
	 */
	private String name;
	
	/**
	 * Score student scored in midterm.
	 */
	private double midtermScore;

	/**
	 * Score student scored in finals.
	 */
	private double finalScore;

	/**
	 * Score student scored in exercises.
	 */
	private double exerciseScore;

	/**
	 * Final grade of the student.
	 */
	private int grade;
	
	/**
	 * Character used to split arguments for constructor.
	 */
	private static final String ARGUMENT_SPLITTER = "\t";	
	
	/**
	 * Position of jmbag in the information.
	 */
	private static final int POSITION_JMBAG = 0;

	/**
	 * Position of surname in the information.
	 */
	private static final int POSITION_SURNAME = 1;

	/**
	 * Position of name in the information.
	 */
	private static final int POSITION_NAME = 2;

	/**
	 * Position of midterm score in the information.
	 */
	private static final int POSITION_MIDTERM_SCORE = 3;

	/**
	 * Position of final score in the information.
	 */
	private static final int POSITION_FINAL_SCORE = 4;

	/**
	 * Position of exercise score in the information.
	 */
	private static final int POSITION_EXERCISE_SCORE = 5;

	/**
	 * Position of grade in the information.
	 */
	private static final int POSITION_GRADE = 6;
	
	
	/**
	 * Creates a record from the given string.
	 * Arguments are split with TAB and contains jmbag,
	 * surname, name, midterm, finals, exercise score and grade in 
	 * that order.
	 * 
	 * @param information containins information of a student
	 */
	public StudentRecord(String information){
		String[] arguments = information.split(ARGUMENT_SPLITTER);
		if(arguments.length != 7){
			throw new IllegalArgumentException("Warning - "
					+ "Given string: "+ information 
					+ " does not contain all "
					+ "information needed!");
		}
		
		jmbag = arguments[POSITION_JMBAG];
		
		surname = arguments[POSITION_SURNAME];
		
		name = arguments[POSITION_NAME];
		
		midtermScore = Double.
				parseDouble(arguments[POSITION_MIDTERM_SCORE]);
		
		finalScore = Double.
				parseDouble(arguments[POSITION_FINAL_SCORE]);
		
		exerciseScore = Double.
				parseDouble(arguments[POSITION_EXERCISE_SCORE]);
		
		grade = Integer.parseInt(arguments[POSITION_GRADE]);
		
	}
	
	
	/**
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @return the midtermScore
	 */
	public double getMidtermScore() {
		return midtermScore;
	}
	
	
	/**
	 * @return the finalScore
	 */
	public double getFinalScore() {
		return finalScore;
	}
	
	
	/**
	 * @return the exerciseScore
	 */
	public double getExerciseScore() {
		return exerciseScore;
	}
	
	
	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}
	
	/**
	 * Gets the combined score of midterms, finals and exercises.
	 * @return returns the score of a student
	 */
	public int getScore(){
		return (int) (exerciseScore + finalScore + midtermScore);
	}
	
	@Override
	public String toString() {
		return jmbag;
	}
}
