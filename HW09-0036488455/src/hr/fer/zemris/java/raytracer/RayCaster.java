package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that paints a scene with ray tracing.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class RayCaster {

	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}


	/**
	 * Creates the ray tracer producer that traces pixels and sends the found
	 * data to a raster.
	 * 
	 * @return returns the tracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D gSubO = view.sub(eye);

				Point3D zAxis = gSubO.scalarMultiply(1. / gSubO.norm());

				Point3D yUnnormalized = viewUp
						.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp)));
				Point3D yAxis = yUnnormalized
						.scalarMultiply(1. / yUnnormalized.norm());

				Point3D xUnnormalized = zAxis.vectorProduct(yAxis);

				Point3D xAxis = xUnnormalized
						.scalarMultiply(1. / xUnnormalized.norm());

				Point3D screenCorner = RayTracer.calculateCorner(view, xAxis, yAxis,
						horizontal, vertical);

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = RayTracer.calculatePoint(
								screenCorner, xAxis, yAxis, x, y, width, height,
								horizontal, vertical);
						Ray ray = Ray.fromPoints(eye, screenPoint);

						RayTracer.trace(scene, ray, rgb, eye);

						red[offset] = rgb[PixelColorizer.RED] > 255 ? 255
								: rgb[PixelColorizer.RED];
						green[offset] = rgb[PixelColorizer.GREEN] > 255 ? 255
								: rgb[PixelColorizer.GREEN];
						blue[offset] = rgb[PixelColorizer.BLUE] > 255 ? 255
								: rgb[PixelColorizer.BLUE];

						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}
}
