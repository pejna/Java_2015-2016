package hr.fer.zemris.java.fractals;

import java.text.ParseException;

import hr.fer.zemris.java.fractals.complex.Complex;
import hr.fer.zemris.java.fractals.complex.ComplexPolynomial;
import hr.fer.zemris.java.fractals.complex.ComplexRootedPolynomial;

/**
 * Factory class that objects of {@link Complex}, {@link ComplexPolynomial} and
 * {@link ComplexRootedPolynomial} classes.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ComplexFactory {

	/**
	 * Creates a complex number from the given string. Accepts strings in forms
	 * a + ib, a + bi, ib, i, a.
	 * 
	 * @param number
	 *            string containing information about the number
	 * @return returns the produced number
	 * @throws ParseException
	 *             thrown if cannot deduce a number from given string
	 */
	public static Complex createComplex(String number) throws ParseException {
		if (number.length() == 0) {
			throw new IllegalArgumentException(
					"Warning - Cannot covert empty string to complex number!");
		}

		try {
			number = number.replaceAll(" ", "").trim();

			if (number.equals("i")) {
				return Complex.IM;
			} else if (number.equals("-i")) {
				return Complex.IM_NEG;
			}

			int firstDigit = -1;
			int iPos = number.length();
			boolean foundFirst = false;
			for (int i = 0; i < number.length(); i++) {
				if (number.charAt(i) == 'i') {
					iPos = i;
				}
				if (!foundFirst && Character.isDigit(number.charAt(i))) {
					firstDigit = i;
					foundFirst = true;
				}
			}

			Complex complex;
			if (iPos < firstDigit) {
				number = number.replaceAll("i", "");
				complex = new Complex(0, Double.parseDouble(number));
			} else {
				while (firstDigit < number.length()) {
					if (!Character.isDigit(number.charAt(firstDigit))) {
						break;
					}
					firstDigit++;
				}

				double real = Double
						.parseDouble(number.substring(0, firstDigit));
				double imaginary = 0;
				if (firstDigit != number.length()) {
					number = number.substring(firstDigit).replaceAll("i", "");
					if (number.length() == 1) {
						number = number + 1;
					}
					imaginary = Double.parseDouble(number);
				}

				complex = new Complex(real, imaginary);

			}

			return complex;

		} catch (Exception e) {
			throw new ParseException(
					"Warning - Couldn't parse given string: " + number, -1);
		}
	}


	/**
	 * Creates a {@link ComplexRootedPolynomial} from the given {@link Complex}
	 * numbers. Each given number is one root. Example: if given an array {a, b}
	 * will produce a rooted polynomial of form (z - a)*(z - b). Must give at
	 * least one root.
	 * 
	 * @param roots
	 *            roots of the number
	 * @return returns the rooted polynomial
	 */
	public static ComplexRootedPolynomial createRootedPolynomial(
			Complex[] roots) {
		return new ComplexRootedPolynomial(roots);
	}


	/**
	 * Creates a {@link ComplexPolynomial} from the given {@link Complex}
	 * factors. Each given number is factor next to the variable the power
	 * of it's index. Example: if given array {a, b, c} will produce a
	 * polynomial of form a + b*z + c*z^2.
	 * 
	 * @param factors factory of the polynomial
	 * @return returs the polynomial
	 */
	public static ComplexPolynomial createPolynomial(Complex[] factors) {
		return new ComplexPolynomial(factors);
	}
}
