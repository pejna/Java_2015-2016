package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;


/**
 * Dosplays the use of {@link ObjectMultistack} and 
 *  {@link ValueWrapper} classes.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ObjectMultistackDemo {

	
	/**
	 * Executes the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		
		System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());
		
		System.out.println("Current value for price: "
					+ multistack.peek("price").getValue());
		
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());
		
		multistack.peek("year").setValue(
		((Integer)multistack.peek("year").getValue()).intValue() + 50
		);
		System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());
		System.out.println("**************************");
		multistack.pop("year");
		System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());
		
		multistack.peek("year").increment("5");
		System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());
		
		multistack.peek("year").increment(5);
		System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());
		
		multistack.peek("year").increment(5.0);
		System.out.println("Current value for year: "
					+ multistack.peek("year").getValue());
	}
}

