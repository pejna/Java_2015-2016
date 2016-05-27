package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;

/**
 * Concrete implementation of {@link RayIntersection}. For further information
 * look at it's documentation.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ConcreteRayIntersection extends RayIntersection {

	/**
	 * Normal vector to the intersection.
	 */
	private Point3D normal;

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
	 * Reflective coefficient for the color of object.
	 */
	private double krn;


	/**
	 * Creates the {@link RayIntersection} with given information. Any given
	 * coefficient must be in the interval [0.0, 1.0] and no points can be null.
	 * 
	 * @param point
	 *            point of the intersection
	 * @param distance
	 *            distance from the point to thestart of the ray
	 * @param outer
	 *            is the intersection outer or inner
	 * @param normal
	 *            normal vector to the point
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
	public ConcreteRayIntersection(Point3D point, double distance,
			boolean outer, Point3D normal, double kdr, double kdg, double kdb,
			double krr, double krg, double krb, double krn) {
		
		super(point, distance, outer);
		Objects.requireNonNull(normal);

		ModelUtil.checkAllRanges(kdr, kdg, kdb, krr, krg, krb);

		this.normal = normal.normalize();

		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}


	@Override
	public Point3D getNormal() {
		return normal;
	}


	@Override
	public double getKdr() {
		return kdr;
	}


	@Override
	public double getKdg() {
		return kdg;
	}


	@Override
	public double getKdb() {
		return kdb;
	}


	@Override
	public double getKrr() {
		return krr;
	}


	@Override
	public double getKrg() {
		return krg;
	}


	@Override
	public double getKrb() {
		return krb;
	}


	@Override
	public double getKrn() {
		return krn;
	}
}
