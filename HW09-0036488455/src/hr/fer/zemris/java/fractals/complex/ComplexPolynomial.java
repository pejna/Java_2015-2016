package hr.fer.zemris.java.fractals.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Polynomial with {@link Complex} instead of usual integers or doubles.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ComplexPolynomial {

	/**
	 * Factors of the polynomial.
	 */
	private final Complex[] factors;


	/**
	 * Creates the polynomial with the give factors. Needs at least 1 factor.
	 * 
	 * @param factors
	 *            factory fo the polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors);
		if (factors.length == 0) {
			throw new IllegalArgumentException(
					"Warning - " + "Required at least 1 member!");
		}

		int lastNonZero = 0;
		for (int i = 0; i < factors.length; i++) {
			if (factors[i].getReal() != 0 || factors[i].getImaginary() != 0) {
				lastNonZero = i;
			}
		}

		Complex[] array = new Complex[lastNonZero + 1];
		for (int i = 0; i <= lastNonZero; i++) {
			array[i] = factors[i];
		}

		this.factors = array;
	}


	/**
	 * Gets the order of the polynomial.
	 * 
	 * @return returns the order of the polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}


	/**
	 * Multipli this polynome with the given polynome.
	 * 
	 * @param p
	 *            polynome with which to multiply
	 * @return returns the multiplication result
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p);

		Complex[] array = new Complex[factors.length + p.factors.length];

		for (int i = 0; i < array.length; i++) {
			array[i] = Complex.ZERO;
		}

		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				array[i + j] = array[i + j]
						.add(factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(array);
	}


	/**
	 * Derivates the polynome.
	 * 
	 * @return returns the derivate
	 */
	public ComplexPolynomial derive() {
		List<Complex> list = new ArrayList<>();

		for (int i = 1; i < factors.length; i++) {
			Complex power = new Complex(i, 0);
			list.add(factors[i].multiply(power));
		}
		if (list.size() == 0) {
			list.add(Complex.ZERO);
		}

		ComplexPolynomial newPolynomial = new ComplexPolynomial(
				list.toArray(new Complex[0]));

		return newPolynomial;
	}


	/**
	 * Applies the given number to the polynome.
	 * 
	 * @param z
	 *            number to be applied
	 * @return returns the calculated polynome with the guven number applied
	 */
	public Complex apply(Complex z) {
		Complex complex = Complex.ZERO;
		for (int i = 0; i < factors.length; i++) {
			complex = complex.add(factors[i].multiply(z.power(i)));
		}

		return complex;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < factors.length; i++) {
			sb.append('(');
			sb.append(factors[i].toString());
			sb.append(')');
			if (i != 0) {
				sb.append(Complex.LETTER_REPRESENTATION).append('^').append(i);
			}
			if (i != factors.length - 1) {
				sb.append(" + ");
			}
		}

		return sb.toString();
	}
}
