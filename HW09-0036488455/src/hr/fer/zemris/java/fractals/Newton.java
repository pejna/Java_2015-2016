
package hr.fer.zemris.java.fractals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.complex.Complex;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;

/**
 * Program that calculates and shows a fractal in a window. Program asks for at
 * least 2 complex numbers. Acceptable formats are a, ib, a + ib, a + bi. When
 * finished with numbers type done.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Newton {

	/**
	 * Word with which to finish the input.
	 */
	private static final String DONE = "done";

	/**
	 * Leas number of accepted inputs.
	 */
	private static final int ACCEPTED_NUMBER = 2;


	/**
	 * Executes the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		System.out.println(
				"Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least " + ACCEPTED_NUMBER
				+ " roots, one root per line. Enter " + DONE + " when done!");

		int i = 1;
		Scanner scanner = new Scanner(System.in);
		String input;
		List<Complex> list = new ArrayList<>();

		while (true) {
			System.out.print("Root " + i + "> ");
			input = scanner.nextLine();
			if (input.trim().toUpperCase().equals(DONE.toUpperCase())) {
				if (i >= ACCEPTED_NUMBER + 1) {
					break;
				} else {
					System.out.println("Not enough imputed roots!");
				}
			} else {
				i++;
				try {
					list.add(ComplexFactory.createComplex(input));
				} catch (ParseException e) {
					System.out.println(
							"Couldn't understand that, please repeat!");
					i--;
				}
			}
		}

		System.out.println("Image of fractal will appear shortly. Thank you.");
		// System.out.println(NewtonRaphsonCalculator.mapToPlain(75, 75, 0, 100,
		// 0, 100, 50, 60, 50, 60));
		FractalViewer.show(new FractalProducerImpl(list));
		scanner.close();
	}

}
