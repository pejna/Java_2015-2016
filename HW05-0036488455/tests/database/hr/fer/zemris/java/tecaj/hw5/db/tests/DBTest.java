package hr.fer.zemris.java.tecaj.hw5.db.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

public class DBTest {

	@Test(expected = IllegalArgumentException.class)
	public void emptyDB() {
		StudentDatabase db = new StudentDatabase(null);
	}

	@Test
	public void filterTestNone() {
		StudentDatabase db = initDB();
		
		List<StudentRecord> list = db.filter((s) -> false);
		assertEquals(0, list.size());
	}
	
	@Test
	public void filterTestAll(){
StudentDatabase db = initDB();
		
		List<StudentRecord> list = db.filter((s) -> true);
		//total of 63 student records in the database
		assertEquals(63, list.size());
	}

	@Test
	public void filterTest3() {
		StudentDatabase db = initDB();
		
		List<StudentRecord> list = db.filter((s) -> s.getJmbag().equals("0000000030"));
		assertEquals("Kovačević", list.get(0).getLastName());
		
	}
	
	@Test
	public void filterTest4() {
		StudentDatabase db = initDB();
		
		List<StudentRecord> list = db.filter((s) -> s.getLastName().equals("Pilat"));
		//only 1 student with last name "Pilat"
		assertEquals("0000000044", list.get(0).getJmbag());	
	}
	
	@Test
	public void filterTest5() {
		StudentDatabase db = initDB();
		
		List<StudentRecord> list = db.filter((s) -> s.getFirstName().equals("Bojan"));
		//only 1 student named "Bojan"
		assertEquals("Krušelj Posavec", list.get(0).getLastName());	
	}
	
	@Test
	public void filterTest6() {
		StudentDatabase db = initDB();
		
		List<StudentRecord> list = db.filter((s) -> s.getFirstName().equals("Ivan"));
		//5 students named "Ivan"
		assertEquals(5, list.size());	
	}
	
	@Test
	public void forJMBAG1() {
		StudentDatabase db = initDB();
		
		StudentRecord rec = db.forJMBAG("0000000043");
		assertEquals("Krešimir", rec.getFirstName());
		assertEquals("Perica", rec.getLastName());
		assertEquals(4, rec.getFinalGrade());
	}
	
	@Test
	public void forJMBAG2(){
		StudentDatabase db = initDB();
		
		StudentRecord rec = db.forJMBAG(null);
		assertEquals(null, rec);
	}
	
	@Test
	public void forJMBAG3(){
		StudentDatabase db = initDB();
		
		//no such JMBAG
		StudentRecord rec = db.forJMBAG("123");
		assertEquals(null, rec);
	}
	
	private StudentDatabase initDB() {
		String file = "E:\\FER\\Java\\HW05-0036488455\\tests\\database\\hr\\fer\\zemris\\java\\tecaj\\hw5\\db\\tests\\test\\database.txt";
		List<String> docBody = null;
		try {
			docBody = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
		} catch (IOException ignorable) {
		}

		StudentDatabase db = new StudentDatabase(docBody);
		return db;
	}
	
	private static String[] getDocBody(List<String> docBody) {
		String[] docArray = new String[docBody.size()];
		int i = 0;
		for (String s : docBody) {
			docArray[i++] = s;
		}
		return docArray;
	}
}
