/**
 * Package contains classes required for second homeword for 
 * Introduction to Java programming language of FER 2016
 * 
 */
package hr.fer.zemris.java.tecaj.hw2;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

/**
 * Complex number with real and imaginary parts.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class ComplexNumber {

	private double real;
	private double imaginary;
	private double magnitude;
	private double angle;


	/**
	 * Creates a complex number with given real and imaginary parts.
	 * 
	 * @param real
	 *            real part of new complex number
	 * @param imaginary
	 *            imaginary part of new complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		this.angle = Math.atan2(imaginary, real) % (2 * Math.PI);
		this.magnitude = Math.hypot(real, imaginary);
	}


	/**
	 * Manufactures a complex number with given number as real part and
	 * imaginary part set to 0.
	 * 
	 * @param real
	 *            real part of manufactured complex number
	 * @return returns manufactured complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}


	/**
	 * Manufactures a complex number with given number as imaginary part and
	 * real part set to 0.
	 * 
	 * @param imaginary
	 *            imaginar part of manufactured complex number
	 * @return returns manufactured complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}


	/**
	 * Manufactures a complex number from given magnitude and angle of polar
	 * form.
	 * 
	 * @param magnitude
	 *            magnitude of complex number polar form
	 * @param angle
	 *            angle of complex number polar form
	 * @return returns manufactured complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) throws IllegalArgumentException {
		if (magnitude <= 0) {
			throw new IllegalArgumentException(
					"Magnitude must " + "be positive.");
		}

		return new ComplexNumber(magnitude * Math.cos(angle % (2 * Math.PI)),
				magnitude * Math.sin(angle % (2 * Math.PI)));
	}


	/**
	 * Parses the given string to produce a complex number. String must contain
	 * real, imaginary or both parts of complex number. Example: "-2.3" or
	 * "3.4i" or "-2.3 + 3.4i"
	 * 
	 * @param s
	 *            string contining complex number
	 * @return returns produces complex number
	 */
	public static ComplexNumber parse(String s) {
		s = s.replace("-", " -");
		s = s.replace("+", " ");
		String[] parts = s.trim().split(" ");

		if (parts.length == 2) {
			parts[0] = parts[0].trim();
			parts[1] = parts[1].replace("i", "").trim();

			return new ComplexNumber(Double.parseDouble(parts[0]),
					Double.parseDouble(parts[1]));
		} else {
			parts[0] = parts[0].trim();

			if (parts[0].endsWith("i")) {
				parts[0] = parts[0].replace("i", "").trim();

				return new ComplexNumber(0, Double.parseDouble(parts[0]));
			} else {

				return new ComplexNumber(Double.parseDouble(parts[0]), 0);
			}
		}
	}


	/**
	 * @return the real part of the complex number
	 */
	public double getReal() {
		return real;
	}


	/**
	 * @return the imaginary of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}


	/**
	 * @return the magnitude of the complex number
	 */
	public double getMagnitude() {
		return magnitude;
	}


	/**
	 * @return the angle of the complex number
	 */
	public double getAngle() {
		return angle;
	}


	/**
	 * Adds this and provided complex numbers.
	 * 
	 * @param c
	 *            the other operand of addition
	 * @return returns the sum of addition
	 * @throws IllegalArgumentException
	 *             thrown if given null
	 */
	public ComplexNumber add(ComplexNumber c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException(
					"Warning - " + "Cannot execute operation with null!");
		}

		return new ComplexNumber(this.real + c.real,
				this.imaginary + c.imaginary);
	}


	/**
	 * Subtracts the given number from this number.
	 * 
	 * @param c
	 *            other operand of subtraction
	 * @return returns the subtraction
	 * @throws IllegalArgumentException
	 *             thrown if given null
	 */
	public ComplexNumber sub(ComplexNumber c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException(
					"Warning - " + "Cannot execute operation with null!");
		}

		return new ComplexNumber(this.real - c.real,
				this.imaginary - c.imaginary);
	}


	/**
	 * Multiplies this number with given number.
	 * 
	 * @param c
	 *            other operand of multiplication
	 * @return returns returns the multiplication
	 * @throws IllegalArgumentException
	 *             thrown if given null
	 */
	public ComplexNumber mul(ComplexNumber c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException(
					"Warning - " + "Cannot execute operation with null!");
		}

		return fromMagnitudeAndAngle(this.magnitude * c.magnitude,
				this.angle + c.angle);
	}


	/**
	 * Divides this number with given number.
	 * 
	 * @param c
	 *            other operand of division
	 * @return returns the division
	 * @throws IllegalArgumentException
	 *             thrown if given null
	 */
	public ComplexNumber div(ComplexNumber c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException(
					"Warning - " + "Cannot execute operation with null!");
		}
		return fromMagnitudeAndAngle(this.magnitude / c.magnitude,
				this.angle - c.angle);
	}


	/**
	 * Raises this number to the given power.
	 * 
	 * @param n
	 *            power we wish this number to be raised to
	 * @return returns this number raised to requested power
	 * @throws IllegalArgumentException
	 *             thrown if requested power is negative
	 */
	public ComplexNumber power(int n) throws IllegalArgumentException {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Warning - " + "Cannot calculate negative power of complex "
							+ "number!");
		}

		ComplexNumber temp = new ComplexNumber(real, imaginary);

		for (int i = 1; i < n; i++) {
			temp = mul(temp);
		}

		return temp;
	}


	/**
	 * Finds all roots of given power of this complex number.
	 * 
	 * @param n
	 *            power of the root
	 * @return returns array of all roots of given power
	 * @throws IllegalArgumentException
	 *             thrown if requested power is not a positive integer
	 */
	public ComplexNumber[] root(int n) throws IllegalArgumentException {
		if (n < 0) {
			throw new IllegalArgumentException("Warning - "
					+ "Cannot calculate negative root of complex " + "number!");
		}

		ComplexNumber[] numbers = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			double real = pow(abs(magnitude), 1. / n)
					* cos((i * 2. * PI / n) + (angle / n));

			double imag = pow(abs(magnitude), 1. / n)
					* sin((i * 2. * PI / n) + (angle / n));

			numbers[i] = new ComplexNumber(real, imag);
		}

		return numbers;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%.2f /_%.2f ", magnitude, angle);
	}

}
