package hr.fer.zemris.java.tecaj.hw5.db;


/**
 * Record representing basic information of the student in a database.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class StudentRecord {

	/**
	 * Jmbag of the student
	 */
	private String jmbag;
	
	/**
	 * Name of the student
	 */
	private String firstName;
	
	/**
	 * Surname of the student
	 */
	private String lastName;
	
	/**
	 * Grade of the student
	 */
	private int finalGrade;
	
	/*
	 * ******** Constants ********************************************
	 */
	
	/**
	 * Supported number of arguments the spring must be split
	 */
	private static final int NUMBER_OF_ARGUMENTS = 4;
	
	/**
	 * Index of the argument representing jmbag
	 */
	private static final int INDEX_JMBAG = 0;
	
	/**
	 * Index of the argument representing last name
	 */
	private static final int INDEX_LAST_NAME = 1;
	
	/**
	 * Index of the arguments representing first name
	 */
	private static final int INDEX_FIRST_NAME = 2;
	
	/**
	 * Index of the argument representing grade
	 */
	private static final int INDEX_GRADE = 3;
	
	
	/*
	 * ******** Constructor methods **********************************
	 */
	
	
	/**
	 * Creates a student record from the given string. Arguments
	 * are jmbag, last name, first name and grade in that order
	 * separated by TAB.
	 * 
	 * @param string string containing arguments
	 */
	public StudentRecord(String string){
		if(string == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot create student record from null!");
		}
		
		String[] arguments = string.trim().split("\t");
		if(arguments.length != NUMBER_OF_ARGUMENTS){
			throw new RuntimeException("Warning - "
					+ "Incorrect number of arguments in the string!");
		}
		
		jmbag = arguments[INDEX_JMBAG];
		lastName = arguments[INDEX_LAST_NAME];
		firstName = arguments[INDEX_FIRST_NAME];
		finalGrade = Integer.parseInt(arguments[INDEX_GRADE]);
		
	}

	
	/*
	 * ******** Getter methods ***************************************
	 */
	
	
	/**
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/*
	 * ******** Utility methods **************************************
	 */
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	

}
