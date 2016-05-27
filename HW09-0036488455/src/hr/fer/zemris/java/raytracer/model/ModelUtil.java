package hr.fer.zemris.java.raytracer.model;


/**
 * Utility class used for checking ranges of numberical representations of
 * diffusion and reflection coefficients in 3D models.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ModelUtil {

	/**
	 * Checks if the given number is inside of the interval. [0.0, 1.0]. Throws
	 * exception if it is not.
	 * 
	 * @param number
	 *            number to be checked
	 * @param name
	 *            name associated with that number, used for error reporting
	 */
	public static void checkRange(double number, String name) {
		if (number < 0 || number > 1) {
			throw new IllegalArgumentException("Warning- Given coeficient: "
					+ name + " is out of bounds!");
		}
	}


	/**
	 * Checks the range of all of the given arguments.
	 * 
	 * @param kdr
	 *            diffuse coefficient for red
	 * @param kdg
	 *            diffuse coefficient for green
	 * @param kdb
	 *            diffuse coefficient for blue
	 * @param krr
	 *            reflective coefficient for red
	 * @param krg
	 *            reflective coefficient for green
	 * @param krb
	 *            reflective coefficient for blue
	 */
	public static void checkAllRanges(double kdr, double kdg, double kdb,
			double krr, double krg, double krb) {
		ModelUtil.checkRange(kdr, "kdr");
		ModelUtil.checkRange(kdg, "kdg");
		ModelUtil.checkRange(kdb, "kdb");
		ModelUtil.checkRange(krr, "krr");
		ModelUtil.checkRange(krg, "krg");
		ModelUtil.checkRange(krb, "krb");

	}
}
