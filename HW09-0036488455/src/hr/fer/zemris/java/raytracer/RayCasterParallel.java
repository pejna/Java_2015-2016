package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;


/**
 * Program that paints a scene with ray casting using parallel computing.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class RayCasterParallel {

	/**
	 * Executes the program.
	 * @param args not used
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(new ParallelRayTracerProducer(),
				new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

}
