package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;

/**
 * Graphical representation of a sphere from which light reflects.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of the sphere.
	 */
	private Point3D center;

	/**
	 * Radius of the sphere.
	 */
	private double radius;

	/**
	 * Diffuse coefficient for red.
	 */
	private double kdr;

	/**
	 * Diffuse coefficient for green.
	 */
	private double kdg;

	/**
	 * Diffuse coefficient for blue.
	 */
	private double kdb;

	/**
	 * Reflective coefficient for red.
	 */
	private double krr;

	/**
	 * Reflective coefficient for blue.
	 */
	private double krb;

	/**
	 * Reflective coefficient for green.
	 */
	private double krg;

	/**
	 * Reflective power for the color of sphere.
	 */
	private double krn;


	/**
	 * Creates the {@link Sphere} with the given parameters.
	 * 
	 * @param center
	 *            center of the sphere
	 * @param radius
	 *            readius of the sphere
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
	 * @param krn
	 *            reflective power for the color of the object
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		Objects.requireNonNull(center);
		if (radius <= 0) {
			throw new IllegalArgumentException(
					"Warning - Radius must be a positive number but was: "
							+ radius + "!");
		}
		ModelUtil.checkAllRanges(kdr, kdg, kdb, krr, krg, krb);

		this.center = center;
		this.radius = radius;

		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}


	/**
	 * Finds the closes intersection of the given {@link Ray} with the
	 * {@link Sphere}.
	 * 
	 * @param ray
	 *            ray which may intersect with sphere
	 * @return returns the intersection data, null if no intersection found
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Objects.requireNonNull(ray);

		// based on formula from
		// https://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection

		double b = ray.direction.scalarProduct(ray.start.sub(center));
		double ac = Math.pow(ray.direction.norm(), 2)
				* (Math.pow(ray.start.sub(center).norm(), 2) - radius * radius);

		double determinant = b * b - ac;

		if (determinant < 0) {
			return null;
		}

		double aSquared = Math.pow(ray.direction.norm(), 2);

		double distance;
		if (determinant == 0) {
			distance = -b / aSquared;
		} else {
			distance = (-b - Math.pow(determinant, 0.5)) / aSquared;
		}

		Point3D point = ray.start.add(ray.direction.scalarMultiply(distance));
		Point3D normal = point.sub(center);
		return new ConcreteRayIntersection(point, distance, true, normal, kdr,
				kdg, kdb, krr, krg, krb, krn);
	}
}
