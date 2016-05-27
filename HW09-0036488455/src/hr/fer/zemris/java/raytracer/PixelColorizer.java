package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Utility class used for coloring pixels with the information from a scene.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class PixelColorizer {

	/**
	 * Position of red component of the array.
	 */
	public static final int RED = 0;

	/**
	 * Position of green component of the array.
	 */
	public static final int GREEN = 1;

	/**
	 * Position of the blue component of the array.
	 */
	public static final int BLUE = 2;

	/**
	 * Base level of light in the scene.
	 */
	private static final int AMBIENT_LIGHT = 15;


	/**
	 * Colorizes the pixel whose rbg components the given array represents.
	 * 
	 * @param rgb
	 *            red, green and blue components of the pixel
	 * @param intersection
	 *            intersection of the traced ray with the nearest object
	 * @param scene
	 *            scene with objects
	 * @param eye
	 *            eye of the beholder
	 */
	public static void colorPixel(short[] rgb, RayIntersection intersection,
			Scene scene, Point3D eye) {
		for (int i = 0; i < rgb.length; i++) {
			rgb[i] = AMBIENT_LIGHT;
		}
		Point3D normal = intersection.getNormal();

		for (LightSource ls : scene.getLights()) {
			Ray lightRay = Ray.fromPoints(ls.getPoint(),
					intersection.getPoint());

			Point3D lightDirection = lightRay.direction.normalize();
			RayIntersection obstruction = RayTracer.getIntersection(scene,
					lightRay);

			Point3D lsPoint = ls.getPoint();
			if (obstruction != null && obstruction
					.getDistance() < intersection.getPoint().sub(lsPoint).norm()
							- 0.001) {
				continue;
			}

			// diffuse calculations
			Point3D lightDirectionReverse = lightDirection.scalarMultiply(-1);
			double diffuseScalar = lightDirectionReverse
					.scalarProduct(intersection.getNormal());

			if (diffuseScalar >= 0) {
				rgb[RED] += diffuseScalar * intersection.getKdr() * ls.getR();
				rgb[GREEN] += diffuseScalar * intersection.getKdg() * ls.getG();
				rgb[BLUE] += diffuseScalar * intersection.getKdb() * ls.getB();
			}

			// reflection calculations
			Point3D reflectionVector = lightDirection.sub(normal
					.scalarMultiply(2 * lightDirection.scalarProduct(normal)));
			Point3D viewVector = Ray.fromPoints(intersection.getPoint(),
					eye).direction;

			double reflectionScalar = Math.pow(
					reflectionVector.scalarProduct(viewVector),
					intersection.getKrn());

			if (reflectionScalar >= 0) {
				rgb[RED] += ls.getR() * intersection.getKrr()
						* reflectionScalar;
				rgb[GREEN] += ls.getG() * intersection.getKrg()
						* reflectionScalar;
				rgb[BLUE] += ls.getB() * intersection.getKrb()
						* reflectionScalar;
			}
		}
	}
}
