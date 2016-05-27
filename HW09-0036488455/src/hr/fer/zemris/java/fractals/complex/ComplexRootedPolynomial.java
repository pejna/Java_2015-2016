package hr.fer.zemris.java.fractals.complex;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of complex rooted polynomial.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ComplexRootedPolynomial {

	/**
	 * Roots of the number.
	 */
	private final Complex[] roots;


	/**
	 * Creates the rootef polynomial. Requires at least one number given.
	 * 
	 * @param roots
	 *            roots of the polynomial
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		Objects.requireNonNull(roots);
		if (roots.length < 1) {
			throw new IllegalArgumentException(
					"Warning - Expeced at least 1 root but got " + roots.length
							+ "!");
		}

		List<Complex> list = new LinkedList<>();
		for (int i = roots.length - 1; i >= 0; i--) {
			list.add(roots[i]);
		}

		this.roots = list.toArray(new Complex[0]);
	}


	/**
	 * Calculates the polynomial with the given number.
	 * 
	 * @param z
	 *            number to calculate with
	 * @return reutrns the result of application
	 */
	public Complex apply(Complex z) {
		Complex complex = Complex.ONE;

		for (Complex c : roots) {
			complex.multiply(z.add(c.negate()));
		}

		return complex;
	}


	/**
	 * Converts the rooted polynomial representation to a normal polynome
	 * representation.
	 * 
	 * @return returns the converted representation
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] array = new Complex[roots.length + 1];
		for (int i = 0; i < array.length; i++) {
			array[i] = Complex.ZERO;
		}

		int possibility = (int) Math.pow(2, roots.length);
		boolean[] multiply = new boolean[roots.length];

		for (int i = 0; i < possibility; i++) {
			for (int j = 0; j < multiply.length; j++) {
				boolean wasTrue = multiply[j];
				multiply[j] = !multiply[j];

				if (!wasTrue) {
					break;
				}
			}

			Complex complex = Complex.ONE;
			int power = 0;
			for (int j = 0; j < multiply.length; j++) {
				if (multiply[j]) {
					complex = complex.multiply(roots[j].negate());
				} else {
					power++;
				}
			}
			array[power] = array[power].add(complex);
		}

		return new ComplexPolynomial(array);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < roots.length; i++) {
			sb.append('(');
			sb.append(Complex.LETTER_REPRESENTATION).append(' ');
			sb.append("- (");
			sb.append(roots[i].toString());
			sb.append("))");
		}

		return sb.toString();
	}


	/**
	 * Finds the index of root closest to the given number whose distance is
	 * lesser than the given treshold
	 * 
	 * @param z
	 *            number to be compared to
	 * @param treshold
	 *            threshold for the comparation
	 * @return returns the index if a number is found, otherwise returns -1
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		for (int i = 0; i < roots.length; i++) {
			double module = z.sub(roots[i]).module();
			if (module <= treshold) {
				treshold = module;
				index = roots.length - i;
			}
		}

		return index;
	}

}
