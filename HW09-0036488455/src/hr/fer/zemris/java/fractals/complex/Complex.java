package hr.fer.zemris.java.fractals.complex;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a complex number. Allows for operations with complex numbers.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Complex {

	/**
	 * Real part of the number.
	 */
	private final double real;

	/**
	 * Imaginary part of the number.
	 */
	private final double imaginary;

	/**
	 * Modulus of the number.
	 */
	private final double modulus;

	/**
	 * Letter representation of imaginary component.
	 */
	private static final char IMAGINARY_LETTER = 'i';

	/**
	 * . * Letter representation of complex numbers.
	 */
	public static final char LETTER_REPRESENTATION = 'z';

	/**
	 * Complex zero.
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Complex one.
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Complex negative one.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Complex imaginary one.
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Complex negative imaginary one.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);


	/**
	 * Creates the zeroed complex number.
	 */
	public Complex() {
		real = 0;
		imaginary = 0;
		modulus = 0;
	}


	/**
	 * Creates the complex nimber with the given components.
	 * 
	 * @param re
	 *            real part of the number
	 * @param im
	 *            imaginary part of the number
	 */
	public Complex(double re, double im) {
		real = re;
		imaginary = im;
		modulus = Math.sqrt(real * real + imaginary * imaginary);

	}


	/**
	 * @return the real
	 */
	public double getReal() {
		return real;
	}


	/**
	 * @return the imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}


	/**
	 * Returns the modulus of the number.
	 * 
	 * @return returns the modulus
	 */
	public double module() {
		return modulus;
	}


	/**
	 * Multiplies this number with the given number. Returns the result as a new
	 * number.
	 * 
	 * @param c
	 *            number to be multiplied with
	 * @return returns the product
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);
		double newReal = this.real * c.real - this.imaginary * c.imaginary;
		double newImaginary = this.imaginary * c.real + this.real * c.imaginary;
		return new Complex(newReal, newImaginary);
	}


	/**
	 * Divides this number with the given number. Returns the result as a new
	 * number.
	 * 
	 * @param c
	 *            number to be divided with
	 * @return returns the division result
	 */
	public Complex divide(Complex c) {
		double newReal = (this.real * c.real + this.imaginary * c.imaginary)
				/ (c.real * c.real + c.imaginary * c.imaginary);
		double newImaginary = (this.imaginary * c.real
				- this.real * c.imaginary)
				/ (c.real * c.real + c.imaginary * c.imaginary);

		return new Complex(newReal, newImaginary);
	}


	/**
	 * Sums this with the given number. Stores the result in new number.
	 * 
	 * @param c
	 *            number to be summed with
	 * @return returns the stum
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);
		double newReal = this.real + c.real;
		double newImaginary = this.imaginary + c.imaginary;
		return new Complex(newReal, newImaginary);
	}


	/**
	 * Subtracts the given number from this number. Stores the result in a new
	 * number.
	 * 
	 * @param c
	 *            number to subtract from this
	 * @return returns the subtraction result
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);
		double newReal = this.real - c.real;
		double newImaginary = this.imaginary - c.imaginary;
		return new Complex(newReal, newImaginary);
	}


	/**
	 * Negates this number.
	 * 
	 * @return returns this number negated
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}


	/**
	 * Calculates this number to the given power. Returns the calculated result.
	 * 
	 * @param n
	 *            power to calculate this number on
	 * @return returns the calculated number
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Warning "
					+ "- Expected power of at least 0, was given " + n + "!");
		}

		Complex complex = ONE;

		while (n > 0) {
			complex = complex.multiply(this);
			n--;
		}

		return complex;
	}


	/**
	 * Calculates the n'th root of this number.
	 * 
	 * @param n
	 *            power of the desired root
	 * @return returns a list contining roots of the given power
	 */
	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Warning "
					+ "- Expected power of at least 1, was given " + n + "!");
		}

		double z = hypot(real, imaginary);
		double t = atan2(imaginary, real);

		List<Complex> list = new ArrayList<>();

		double tempReal;
		double tempImaginary;

		for (int i = 0; i < n; i++) {
			tempReal = pow(abs(z), 1. / n) * cos((i * 2. * PI / n) + (t / n));
			tempImaginary = pow(abs(z), 1. / n)
					* sin((i * 2. * PI / n) + (t / n));

			list.add(new Complex(tempReal, tempImaginary));
		}

		return list;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (imaginary == 0) {
			sb.append(real);

		} else if (real == 0 && imaginary != 0) {
			sb.append(imaginary);
			sb.append(IMAGINARY_LETTER);

		} else {
			sb.append(real);
			sb.append(' ');
			if (imaginary > 0) {
				sb.append('+');
			}
			sb.append(imaginary);
			sb.append(IMAGINARY_LETTER);
		}

		return sb.toString();
	}

}
