package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.complex.Complex;
import hr.fer.zemris.java.fractals.complex.ComplexPolynomial;
import hr.fer.zemris.java.fractals.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * {@link IFractalProducer} implementation that produces fractals with
 * Newton-Raphson method.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class FractalProducerImpl implements IFractalProducer {

	/**
	 * Pool containign the working threads.
	 */
	private ExecutorService pool;

	/**
	 * Factor for the number of jobs.
	 */
	private static int JOB_SCALE = 8;

	/**
	 * Root of the polynomial to calculate with.
	 */
	private ComplexRootedPolynomial root;

	/**
	 * Polynome to calculate with.
	 */
	private ComplexPolynomial polynome;

	/**
	 * Derivate f the polynome to calculate with.
	 */
	private ComplexPolynomial derivate;

	/**
	 * Number of jobs.
	 */
	private int numberOfJobs;


	/**
	 * Creates the producer implementation.
	 * 
	 * @param list
	 *            list of complex numbers to create the rooted polynome from
	 */
	public FractalProducerImpl(List<Complex> list) {
		Objects.requireNonNull(list);

		numberOfJobs = JOB_SCALE * Runtime.getRuntime().availableProcessors();
		pool = Executors.newFixedThreadPool(
				Runtime.getRuntime().availableProcessors(),
				new DaemonicThreadFactory());

		root = new ComplexRootedPolynomial(list.toArray(new Complex[0]));
		polynome = root.toComplexPolynom();
		derivate = polynome.derive();
	}

	/**
	 * Job to be given to working threads.
	 *
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	public static class Job implements Runnable {

		/**
		 * Minimum of the real domain.
		 */
		double reMin;

		/**
		 * Maximum of the real domain.
		 */
		double reMax;

		/**
		 * Minimum of the imaginary domain.
		 */
		double imMin;

		/**
		 * Maximum of the imaginary domain.
		 */
		double imMax;

		/**
		 * Width of the raster.
		 */
		int width;

		/**
		 * Height of the raster.
		 */
		int height;

		/**
		 * Minimum calculated coordinate on the y axis. Inclusive.
		 */
		int yMin;

		/**
		 * Maximum calculated coordinate on the x axis. Non inclusive.
		 */
		int yMax;

		/**
		 * Root of the polynomial to calculate with.
		 */
		private ComplexRootedPolynomial root;

		/**
		 * Polynome to calculate with.
		 */
		private ComplexPolynomial polynome;

		/**
		 * Derivate f the polynome to calculate with.
		 */
		private ComplexPolynomial derivate;

		/**
		 * Array containing the data of the raster.
		 */
		short[] data;

		/**
		 * Maximum number of iterations to pass through the calculation.
		 */
		private static final int maxIter = 16 * 16 * 16;


		/**
		 * Creates the job to work with the given parameters.
		 * 
		 * @param root
		 *            rooted polynomial
		 * @param polynom
		 *            polynom for calculated from the root, given for faster
		 *            calculation
		 * @param derivate
		 *            derivate of the given polynom, given for faster
		 *            calculation
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
		public Job(double reMin, double reMax, double imMin, double imMax,
				int width, int height, int yMin, int yMax,
				ComplexRootedPolynomial root, ComplexPolynomial polynome,
				ComplexPolynomial derivate, short[] data) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.root = root;
			this.polynome = polynome;
			this.derivate = derivate;
			this.data = data;
		}


		@Override
		public void run() {
			NewtonRaphsonCalculator.calculate(root, polynome, derivate, reMin,
					reMax, imMin, imMax, width, height, yMin, yMax, data,
					maxIter);
		}

	}


	/**
	 * Produces the raster filled with calculations of the fractal.
	 * Uses given oberserver and it's method apply to return the calculated 
	 * raster.
	 */
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax,
			int width, int height, long requestNo,
			IFractalResultObserver observer) {

		short[] data = new short[width * height];
		int jobRows = height / numberOfJobs;

		List<Future<?>> results = new ArrayList<>();

		for (int i = 0; i < numberOfJobs; i++) {
			int yMin = i * jobRows;
			int yMax = (i + 1) * jobRows;

			if (i == numberOfJobs - 1) {
				yMax = height;
			}

			Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin,
					yMax, root, polynome, derivate, data);
			results.add(pool.submit(job));
		}

		for (Future<?> job : results) {
			try {
				job.get();
			} catch (InterruptedException | ExecutionException ignorable) {
			}
		}

		observer.acceptResult(data, (short) (polynome.order() + 1), requestNo);
	}


	/**
	 * Closes the pool and finalizes the object.
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		pool.shutdown();
	}

}
