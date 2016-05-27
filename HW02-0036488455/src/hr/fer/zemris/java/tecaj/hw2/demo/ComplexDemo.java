/**
 * Package contains demostration class for demonstratin ComplexNumber
 * class.
 */
package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;


/**
 * Demonstrates the ComplexNumber class.
 * @author Juraj Pejnovic
 * @version 1.1
 */
public class ComplexDemo {

	
	/**
	 * Starts the execution of the program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("-2.5");
		ComplexNumber c3 = c1.add(ComplexNumber.
							fromMagnitudeAndAngle(2, 1.57))
							.div(c2).power(3).root(2)[1];
		ComplexNumber c4 = ComplexNumber.
							fromMagnitudeAndAngle(2, 1.57);
		System.out.println(c3);
		System.out.println(c4);
		

		
	}

}
