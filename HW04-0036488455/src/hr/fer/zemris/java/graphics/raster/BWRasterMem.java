package hr.fer.zemris.java.graphics.raster;


/**
 * Raster representation that holds the raster in it's memory.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class BWRasterMem implements BWRaster{

	/**
	 * Width of the raster.
	 */
	private int width;
	
	/**
	 * Height of the raster.
	 */
	private int height;
	
	/**
	 * Memory containing the raster pixels.
	 */
	boolean[][] memory;
	
	/**
	 * Whether the flip mode is turned on.
	 */
	private boolean flipMode;
	
	/**
	 * Pixels state accepted as on.
	 */
	private static final boolean ON = true;
	
	/**
	 * Pixel state accepted as off.
	 */
	private static final boolean OFF = false;
	
	
	/*
	 * ******** Constructor methods **********************************
	 */
	
	
	/**
	 * Creates the raster with the given parameters.
	 * 
	 * @param width width of the raster
	 * @param height height of the raster
	 */
	public BWRasterMem(int width, int height) {
		if(width < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have width less than 1!");
		}
		
		if(height < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have height less than 1!");
		}
		
		this.width = width;
		this.height = height;
		flipMode = OFF;
		memory = new boolean[width][height];
		clear();
	}
	
	
	/*
	 * ******** Utility methods **************************************
	 */
	
	
	@Override
	public void clear() {
		for(int i = 0; i < width; i++){
			for(int j = 0 ; j < height; j++){
				memory[i][j] = false;
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		if(x < 0 || x >= width){
			throw new IllegalArgumentException("Warning - "
					+ "X coordinate not inside raster!");
		}
		
		if( y < 0 || y >= height){
			throw new IllegalArgumentException("Warning - "
					+ "Y coordinate not inside raster!");
		}
		
		if(flipMode){
			memory[x][y] = !memory[x][y];
		} else {
			memory[x][y] = ON;
		}
	}

	@Override
	public void turnOff(int x, int y) {
		if(x < 0 || x >= width){
			throw new IllegalArgumentException("Warning - "
					+ "X coordinate not inside raster!");
		}
		
		if( y < 0 || y >= height){
			throw new IllegalArgumentException("Warning - "
					+ "Y coordinate not inside raster!");
		}

		memory[x][y] = OFF;		
	}

	
	@Override
	public void enableFlipMode() {
		flipMode = ON;
	}

	
	@Override
	public void disableFlipMode() {
		flipMode = OFF;
	}

	
	@Override
	public boolean isTurnedOn(int x, int y) {
		if(x < 0 || x >= width){
			throw new IllegalArgumentException("Warning - "
					+ "X coordinate not inside raster!");
		}
		
		if( y < 0 || y >= height){
			throw new IllegalArgumentException("Warning - "
					+ "Y coordinate not inside raster!");
		}

		return memory[x][y];
	}

	
	/*
	 * ******** Getter methods ***************************************
	 */
	
	
	@Override
	public int getWidth() {
		return width;
	}

	
	@Override
	public int getHeight() {
		return height;
	}

}
