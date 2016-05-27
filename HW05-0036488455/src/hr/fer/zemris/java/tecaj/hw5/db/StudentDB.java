package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj.hw5.collections.Hashtable;
import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.db.commands.ExitCommand;
import hr.fer.zemris.java.tecaj.hw5.db.commands.ICommand;
import hr.fer.zemris.java.tecaj.hw5.db.commands.IndexQueryCommand;
import hr.fer.zemris.java.tecaj.hw5.db.commands.QueryCommand;
import hr.fer.zemris.java.tecaj.hw5.db.getters.FirstNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.GradeGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.JmbagGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.LastNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.graphics.QueryDrawer;

/**
 * Runs a database program. Can take indexquery, query and exit
 * commands. 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class StudentDB {

	/**
	 * Contains all commands.
	 */
	private static Hashtable<String, ICommand> commands;
	
	/**
	 * Draws found records to desired output.
	 */
	private static QueryDrawer drawer;
	
	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		StudentDatabase database = new StudentDatabase(readLines());


		commands = new SimpleHashtable<>();

		ICommand command = new QueryCommand(database);
		commands.put(command.getKeyword(), command);
		
		command = new IndexQueryCommand(database);
		commands.put(command.getKeyword(), command);

		command = new ExitCommand();
		commands.put(command.getKeyword(), command);
		
		drawer = new QueryDrawer(new JmbagGetter(),
				new LastNameGetter(),
				new FirstNameGetter(),
				new GradeGetter());
	
		String string;
		while((string = reader.nextLine()).length() != 0){
			executeLine(string);
		}
		commands.get("exit").execute(null);
		reader.close();
		
		return;
	}
	

	/**
	 * Reads lines from the database.txt file.
	 * 
	 * @return returns a list of lines read
	 */
	private static List<String> readLines(){
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(
					 Paths.get("./database.txt"),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.err.println("Aborting - "
					+ "Unable to load from given path!");
			System.exit(-1);
		}
		
		return lines;
	}
	

	/**
	 * Executes a query line.
	 * 
	 * @param line query in form of a string
	 */
	private static void executeLine(String line){
		line = line.trim();
		int firstWhitespace = 0;
		while(!Character.isWhitespace(line.charAt(firstWhitespace))){
			firstWhitespace++;
			if(firstWhitespace == line.length()){
				break;
			}
		}
		String command = line.substring(0, firstWhitespace);
		
		String body = null;
		if(firstWhitespace != line.length()){
			body = line.substring(firstWhitespace + 1);
		}
		try {
		List<StudentRecord> returned = 
				(List<StudentRecord>)
				commands.get(command).execute(body);
		
		drawer.print(returned);
		} catch (Exception e){
			System.out.println("Unrecognized command, try again!");
		}
	}
	
	
}
