package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Enables the {@link RasterView} to be viewed in various formats
 * based on implementation.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface RasterView {

	
	/**
	 * Produces a format so that the given raste can be viewed.
	 * 
	 * @param raster raster to be viewed
	 * @return returns the produced object
	 */
	Object produce(BWRaster raster);
}
