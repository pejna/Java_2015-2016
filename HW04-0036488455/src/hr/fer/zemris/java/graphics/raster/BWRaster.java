package hr.fer.zemris.java.graphics.raster;

/**
 * A black and white raster with pixels with only two possible colors.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public interface BWRaster {
	
	/**
	 * Clears the entire raster, turning all pixels off.
	 */
	void clear();
	
	
	/**
	 * Turns the requested pixel on or switches it's value if
	 * the flipmode is on. Throws {@link IllegalArgumentException} if
	 * given indexes out of bounds.
	 * 
	 * @param x x coordinate of the pixel
	 * @param y y coordinate of the pixel
	 */
	void turnOn(int x, int y);
	
	
	/**
	 * Turns the requested pixel off.
	 * Throws {@link IllegalArgumentException} if
	 * given indexes out of bounds.
	 * 
	 * @param x x coordinate of the pixel
	 * @param y y coordinate of the pixel
	 */
	void turnOff(int x, int y);
	
	
	/**
	 * Enables the flip mode. Flip mode flips the value of he pixel 
	 * instead of turning them on.
	 */
	void enableFlipMode();
	
	
	/**
	 * Disables the flip mode.
	 */
	void disableFlipMode();
	
	
	/**
	 * Checks whether the requested pixel is turned on. Throws
	 * {@link IllegalArgumentException} if requested pixel is out of
	 * bounds.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	boolean isTurnedOn(int x, int y);
	
	
	/*
	 * ******** Getter methods ***************************************
	 */
	
	
	/**
	 * Gets the width of the raster.
	 * 
	 * @return returns the width
	 */
	int getWidth();
	
	
	/**
	 * Gets the height of the raster.
	 * @return returns the height
	 */
	int getHeight();
}
