package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;


/**
 * A geometric shape with 4 sides and 90° angles.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public abstract class RectangleLike extends GeometricShape {

	/**
	 * X coordinate of the upper left corner.
	 */
	int firstX;
	
	/**
	 * Y coordinate of the upper left corner.
	 */
	int firstY;
	
	/**
	 * Width of the rectangle-like shape.
	 */
	int width;
	
	/**
	 * Height of the rectangle-like shape.
	 */
	int height;
	
	
	/**
	 * Creates a rectangle-like shape with the given parameters.
	 * 
	 * @param x x coordinate of the upper left corner of the 
	 * rectangle-like shape
	 * @param y y coordinate of the upper left cornef of the 
	 * rectangle-like shape
	 * @param width width of the rectangle-like shape
	 * @param height height of the rectangle-like shape
	 */
	public RectangleLike(int x, int y, int width, int height){
		if(width < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have width less than 1!");
		}
		
		if(height < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have height less than 1!");
		}
		
		this.firstX = x;
		this.firstY = y;
		this.width = width;
		this.height = height;
	}
	
	
	@Override
	public boolean containsPoint(int x, int y) {
		if( x < firstX){
			return false;
		}
		if(x >= firstX + width){
			return false;
		}
		
		if( y < firstY){
			return false;
		}
		if(y >= firstY + height){
			return false;
		}
		
		return true;
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.GeometricShape#draw(hr.fer.zemris.java.graphics.raster.BWRaster)
	 */
	@Override
	public void draw(BWRaster r) {
		int startX = 0;
		int startY = 0;
		
		if(firstX > startX){
			startX = firstX;
		}
		if(firstY > startY){
			startY = firstY;
		}
		
		int endX = r.getWidth();
		int endY = r.getHeight();
		
		if(firstX + width < endX){
			endX = firstX + width;
		}
		if(firstY + height < endY){
			endY = firstY + height;
		}

		for(int y = startY; y < endY; y++){
			for(int x = startX;	x < endX; x++){
				if(containsPoint(x, y)){
					r.turnOn(x, y);
				}
			}
		}
	}
	
	
	/*
	 * ******** Getter methods ***************************************
	 */
	
	
	/**
	 * Gets the upper left x.
	 * @return the x
	 */
	public int getX() {
		return firstX;
	}



	/**
	 * Gets the upper left y.
	 * @return the y
	 */
	public int getY() {
		return firstY;
	}
	
	
	/*
	 * ******** Setter methods ***************************************
	 */
	

	/**
	 * Sets the upper left x of the rectangle.
	 * @param x the upper left x of the rectangle
	 */
	public void setX(int x) {
		this.firstX = x;
	}


	/**
	 * Sets the upper left y of the rectangle.
	 * @param y the upper left y of the rectangle
	 */
	public void setY(int y) {
		this.firstY = y;
	}

}
