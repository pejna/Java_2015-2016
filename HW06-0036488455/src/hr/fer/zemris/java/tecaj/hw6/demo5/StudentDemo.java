package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * Class contains proram that demonstrates the usage of java streams.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class StudentDemo {

	/**
	 * Score used in one of the tests. Score the student has earned.
	 */
	private static final int SCORE = 25;
	
	/**
	 * Grade treated as great success.
	 */
	private static final int EXCELLENT = 5;

	/**
	 * Grade treated as shamefull display.
	 */
	private static final int FAILED = 1;

	
	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			 lines = Files.readAllLines(
					Paths.get("./studenti.txt"),
					 StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Aborting - "
					+ "Couldn't find file to load from!");
			System.exit(-1);
		}
		List<StudentRecord> records = convert(lines);
		
		System.out.println(calculateStudentsWithScore(records));
		System.out.println();
		
		System.out.println(calculateStudentsWithExcellent(records));
		System.out.println();
		
		System.out.println(studentsWithExcellent(records));
		System.out.println();
		
		System.out.println(studentsWithExcellentSorted(records));
		System.out.println();
		
		System.out.println(studentsFailedSorted(records));
		System.out.println();
		
		System.out.println(studentsToGradeLevels(records));
		System.out.println();
		
		System.out.println(studentsToGradeLevelsCount(records));
		System.out.println();
		
		System.out.println(divideStudentsByPassing(records));
	}
	
	
	/**
	 * Converts the given {@link List} of {@link String} to a list
	 * of {@link StudentRecord}s with information contained in
	 * the {@link String}s.
	 * 
	 * @param lines list to be converted
	 * @return returns the converted list
	 */
	private static List<StudentRecord> convert(List<String> lines){
		 return lines.stream()
			.map(s -> new StudentRecord(s))
			.collect(Collectors.toList());
	}
	
	
	/**
	 * Creates a list of students with scores above the score 
	 * constant.
	 * 
	 * @param list list of students to be filtered
	 * @return returnthe filtered students
	 */
	private static long 
			calculateStudentsWithScore(List<StudentRecord> list){
		return list.stream()
				.filter(s -> s.getScore() > SCORE)
				.count();
	}
	
	
	/**
	 * Counts the number of students who scored excellent in their
	 * semesters.
	 * 
	 * @param list list of students to be counted
	 * @return returns the number of excellent students
	 */
	private static long 
			calculateStudentsWithExcellent(List<StudentRecord> list){
		return list.stream()
				.filter(s -> s.getGrade() == EXCELLENT)
				.count();
	}
	
	
	/**
	 * Creates a list of excellent students.
	 * 
	 * @param list list to be sorted
	 * @return returns the list of excellent students
	 */
	private static List<StudentRecord> 
			studentsWithExcellent(List<StudentRecord> list){
		return list.stream()
				.filter(s -> s.getGrade() == EXCELLENT)
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Creates a list of excellent students sorted by their scores
	 * from best to worst.
	 * 
	 * @param list list to be sorted
	 * @return returns the list of excellent students
	 */
	private static List<StudentRecord>
			studentsWithExcellentSorted(List<StudentRecord> list){
		return list.stream()
				.filter(s -> s.getGrade() == EXCELLENT)
				.sorted((s1, s2) -> 
					Integer.compare(s2.getScore(), s1.getScore()))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Creates a list of jmbags of students who failed.
	 * @param list list of students to be sorted
	 * @return returns the list of jmbags of failed students
	 */
	private static List<String>
			studentsFailedSorted(List<StudentRecord> list){
		return list.stream()
				.filter(s -> s.getGrade() == FAILED)
				.sorted((s1, s2) -> 
						s1.getJmbag().compareTo(s2.getJmbag()))
				.map(s -> s.getJmbag())
				.collect(Collectors.toList());
	}
		
	
	/**
	 * Divides the students in their grade ranks.
	 * @param list list of students to be divided
	 * @return returns a map with keys grades and values list of
	 * students with that grade
	 */
	private static Map<Integer, List<StudentRecord>>
			studentsToGradeLevels(List<StudentRecord> list){
		return list.stream()
				.collect(Collectors
						.groupingBy(StudentRecord::getGrade
								, Collectors.toList()));
	}
	
	/**
	 * Counts number of students with the according grades and
	 * maps them to the grades.
	 * @param list list to be grouped
	 * @return returns the map of grades and numbers of students with
	 * that grade
	 */
	private static Map<Integer, Integer>
			studentsToGradeLevelsCount(List<StudentRecord> list){
		return list.stream()
				.collect(Collectors
						.toMap(StudentRecord::getGrade
								, s -> 1, Integer::sum));
	}
	
	
	/**
	 * Divides the students if they passed or not.
	 * 
	 * @param list list of the students to be divided
	 * @return returns map of students with key true if they passed,
	 * or false if they failed
	 */
	private static Map<Boolean, List<StudentRecord>>
			divideStudentsByPassing(List<StudentRecord> list){
		return list.stream()
				.collect(Collectors
						.partitioningBy(s -> s.getGrade() != FAILED));
	}
}
