package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;


/**
 * A geometric shape that is able to be represented on a 2D raster.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public abstract class GeometricShape {

	
	/**
	 * Draws the geometric shape on the given raster. Throws 
	 * {@link IllegalArgumentException} if given null.
	 * 
	 * @param r raster to which to draw the geometric shape
	 */
	public void draw(BWRaster r){
		if(r == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot draw on null raster!");
		}
		
		
		for(int i = 0, width = r.getWidth(); i< width; i++){
			for(int j = 0, height = r.getHeight(); j < height; j++){
				if(containsPoint(i, j)){
					r.turnOn(i, j);
				}
			}
		}
	}
	
	
	/**
	 * Checks if the given point is inside the shape.
	 * 
	 * @param x x parameter of the point
	 * @param y y parameter of the point
	 * @return returns true if inside, otherwise false
	 */
	public abstract boolean containsPoint(int x, int y);
	
}
