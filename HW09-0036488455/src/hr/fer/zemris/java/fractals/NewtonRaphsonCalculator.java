package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.complex.Complex;
import hr.fer.zemris.java.fractals.complex.ComplexPolynomial;
import hr.fer.zemris.java.fractals.complex.ComplexRootedPolynomial;

/**
 * Contains methods for calculating fractals with the Nuewton-Raphson method.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class NewtonRaphsonCalculator {

	/**
	 * Treshold of accepted number. Anything above that will be treted as not
	 * having it's rooted index.
	 */
	private static final double TRESHOLD = 0.002;


	/**
	 * Calculates the fractal. Returns through the given data array.
	 * 
	 * @param root
	 *            rooted polynomial
	 * @param polynom
	 *            polynom for calculated from the root, given for faster
	 *            calculation
	 * @param derivate
	 *            derivate of the given polynom, given for faster calculation
	 * @param reMin
	 *            minimum of the real domain
	 * @param reMax
	 *            maximum of the real domain
	 * @param imMin
	 *            minimum of the imaginary domain
	 * @param imMax
	 *            maximum of the imaginary domain
	 * @param width
	 *            width of the raster
	 * @param height
	 *            height of the raster
	 * @param yMin
	 *            minimum row computed
	 * @param yMax
	 *            maximum row computed, non inclusive
	 * @param data
	 *            array containing raster of dimensions width*height
	 * @param maxIter
	 *            maximum number of iterations calculated
	 */
	public static void calculate(ComplexRootedPolynomial root,
			ComplexPolynomial polynom, ComplexPolynomial derivate, double reMin,
			double reMax, double imMin, double imMax, int width, int height,
			int yMin, int yMax, short[] data, int maxIter) {

		for (int y = yMin; y < yMax; y++) {
			for (int x = 0; x < width; x++) {
				double realPart = x / (width - 1.0) * (reMax - reMin) + reMin;
				double imaginaryPart = (height - 1.0 - y) / (height - 1)
						* (imMax - imMin) + imMin;
				Complex zn = new Complex(realPart, imaginaryPart);
				Complex znPast = zn;

				int counter = 0;

				do {
					znPast = zn;
					zn = znPast
							.sub(polynom.apply(zn).divide(derivate.apply(zn)));
					counter++;
				} while (zn.sub(znPast).module() > TRESHOLD
						&& counter <= maxIter);

				int index = root.indexOfClosestRootFor(zn, TRESHOLD);
				if (index == -1) {
					index = 0;
				}

				data[(y) * width + x] = (short) (index);
			}
		}

	}
}
