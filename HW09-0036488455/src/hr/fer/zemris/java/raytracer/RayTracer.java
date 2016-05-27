package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Utility class used for tracing objects and mapping them to a 2D plane.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class RayTracer {

	/**
	 * Traces the scene with the given ray and paints what was found in the
	 * given array.
	 * 
	 * @param scene
	 *            scene with objects
	 * @param ray
	 *            ray we trace with
	 * @param rgb
	 *            array holding information about colors, red, green and blue
	 * @param eye
	 *            position of the viewer
	 */
	public static void trace(Scene scene, Ray ray, short[] rgb, Point3D eye) {
		RayIntersection intersection = getIntersection(scene, ray);

		if (intersection == null) {
			for (int i = 0; i < rgb.length; i++) {
				rgb[i] = 0;
			}
			return;
		}

		PixelColorizer.colorPixel(rgb, intersection, scene, eye);
	}


	/**
	 * Gets the intersection closest to the start of the given ray if present.
	 * If no intersection is found returns null.
	 * 
	 * @param scene
	 *            scene with objects
	 * @param ray
	 *            ray we trace with
	 * @return returns intersection info if found, null if no itersection exists
	 */
	public static RayIntersection getIntersection(Scene scene, Ray ray) {
		RayIntersection intersection = null;
		for (GraphicalObject o : scene.getObjects()) {
			RayIntersection current = o.findClosestRayIntersection(ray);

			if (current == null) {
				continue;
			}
			if (intersection == null) {
				intersection = current;
				continue;
			}
			if (current.getDistance() <= intersection.getDistance()) {
				intersection = current;
			}
		}

		return intersection;
	}


	/**
	 * Calculates a point in 3D space from given parameters. Corner, xaxis and
	 * yAxis are used for pinpointing the plane on which the point is placed.
	 * 
	 * @param corner
	 *            upper left corner of the plane
	 * @param xAxis
	 *            x axis of the plane
	 * @param yAxis
	 *            y axis of the plane
	 * @param x
	 *            x coordinate of the raster
	 * @param y
	 *            y coordinate of the raster
	 * @param width
	 *            width of the raster
	 * @param height
	 *            height of the raster
	 * @param horizontal
	 *            horizontal width of the complex plane
	 * @param vertical
	 *            vertical heigh of the complex plane
	 * @return returns the coordinates of the point in
	 */
	public static Point3D calculatePoint(Point3D corner, Point3D xAxis,
			Point3D yAxis, int x, int y, int width, int height,
			double horizontal, double vertical) {
		return corner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
				.sub(yAxis.scalarMultiply(vertical * y / (height - 1)));
	}


	/**
	 * Calculates the upper left corner of a viewport defied by the given
	 * points.
	 * 
	 * @param view
	 *            coordinates of the viewport center
	 * @param xAxis
	 *            vector of x axis
	 * @param yAxis
	 *            vector of y axis
	 * @param horizontal
	 *            horizontal width in complex plane
	 * @param vertical
	 *            vertical height in complex plane
	 * @return returns the coordinates of upper left corner
	 */
	public static Point3D calculateCorner(Point3D view, Point3D xAxis,
			Point3D yAxis, double horizontal, double vertical) {

		return view.sub(xAxis.scalarMultiply(horizontal / 2))
				.add(yAxis.scalarMultiply(vertical / 2));
	}

}
