package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * {@link IRayTracerProducer} implementation that uses parallel computing for
 * putting out the view picture.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ParallelRayTracerProducer implements IRayTracerProducer {

	@Override
	public void produce(Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width, int height,
			long requestNo, IRayTracerResultObserver observer) {
		short[] red = new short[width * height];
		short[] green = new short[width * height];
		short[] blue = new short[width * height];

		Point3D gSubO = view.sub(eye);

		Point3D zAxis = gSubO.scalarMultiply(1. / gSubO.norm());

		Point3D yUnnormalized = viewUp
				.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp)));
		Point3D yAxis = yUnnormalized.scalarMultiply(1. / yUnnormalized.norm());

		Point3D xUnnormalized = zAxis.vectorProduct(yAxis);

		Point3D xAxis = xUnnormalized.scalarMultiply(1. / xUnnormalized.norm());

		Point3D screenCorner = RayTracer.calculateCorner(view, xAxis, yAxis,
				horizontal, vertical);

		Scene scene = RayTracerViewer.createPredefinedScene();

		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new Job(scene, screenCorner, eye, xAxis, yAxis, width,
				height, horizontal, vertical, red, green, blue, 0, height));
		pool.shutdown();

		observer.acceptResult(red, green, blue, requestNo);

	}

	/**
	 * Recursive job to process the data of the picture.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private static class Job extends RecursiveAction {

		/**
		 * Serial version of the class-
		 */
		private static final long serialVersionUID = -7506297278471956228L;

		/**
		 * Scene with objects.
		 */
		private Scene scene;

		/**
		 * Upper left corner of the viewport.
		 */
		private Point3D screenCorner;

		/**
		 * The eye of the beholder.
		 */
		private Point3D eye;

		/**
		 * X axis of the viewport.
		 */
		private Point3D xAxis;

		/**
		 * Y axis of the viewport.
		 */
		private Point3D yAxis;

		/**
		 * Width of the pixel row of the screen of the vieweport.
		 */
		private int width;

		/**
		 * Height of the pixel row of the screen of the viewport.
		 */
		private int height;

		/**
		 * Horizontal width of the viewport complex plane.
		 */
		private double horizontal;

		/**
		 * Vertical height of the viewport complex plane.
		 */
		private double vertical;

		/**
		 * Array with red pixel components.
		 */
		private short[] red;

		/**
		 * Array with green pixel components.
		 */
		private short[] green;

		/**
		 * Array with blue pixel components.
		 */
		private short[] blue;

		/**
		 * Minimal height for which to check.
		 */
		private int yMin;

		/**
		 * Maximal height for which to check.
		 */
		private int yMax;

		/**
		 * Threshold for desired number of rows checked by a single job.
		 */
		private final int treshold;


		/**
		 * Creates a job with given parameters.
		 * 
		 * @param scene scene with objects
		 * @param screenCorner corner of the viewport
		 * @param eye eye of the beholder
		 * @param xAxis x axis of the viewport
		 * @param yAxis y axis of the viewport
		 * @param width width of the screen
		 * @param height height of the screen
		 * @param horizontal horizontal width of the viewport
		 * @param vertical vertical height of the viewport
		 * @param red red component of the raster
		 * @param green green component of the raster
		 * @param blue blue component of the raster
		 * @param yMin minimal height for which to calculate
		 * @param yMax maximal height for which to calculate, non inclusive
		 */
		public Job(Scene scene, Point3D screenCorner, Point3D eye,
				Point3D xAxis, Point3D yAxis, int width, int height,
				double horizontal, double vertical, short[] red, short[] green,
				short[] blue, int yMin, int yMax) {
			this.scene = scene;
			this.screenCorner = screenCorner;
			this.eye = eye;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.yMin = yMin;
			this.yMax = yMax;

			treshold = height / 4 * Runtime.getRuntime().availableProcessors();

		}


		/**
		 * Computes the job.
		 */
		@Override
		protected void compute() {
			if (yMax - yMin <= treshold) {
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = yMin; y < yMax; y++) {
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
				return;
			}

			invokeAll(
					new Job(scene, screenCorner, eye, xAxis, yAxis, width,
							height, horizontal, vertical, red, green, blue,
							yMin, yMax / 2),
					new Job(scene, screenCorner, eye, xAxis, yAxis, width,
							height, horizontal, vertical, red, green, blue,
							yMax / 2, yMax));
		}

	}

}
