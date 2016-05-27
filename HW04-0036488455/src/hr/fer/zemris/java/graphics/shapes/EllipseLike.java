package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Geometric shape with curved sides. 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public abstract class EllipseLike extends GeometricShape {

	/**
	 * X coordinate of the center of ellipse-like shape.
	 */
	int centerX;
	
	/**
	 * Y coordinate of the center of ellipse-like shape.
	 */
	int centerY;
	
	/**
	 * Horizontal radius of the ellipse-like shape.
	 */
	int horizontalRadius;
	
	/**
	 * Horizontal radius of the ellipse-like shape.
	 */
	int verticalRadius;
	
	
	/**
	 * Creates an ellipse-like shape from the given parameters.
	 * 
	 * @param x x coordinate of the center of ellipse-like shape
	 * @param y y coordinate of the center of ellipse-like shape
	 * @param horizontalRadius horizontal radius of the ellipse-like
	 * @param verticalRadius vertical radius of the ellipse-like shape
	 */
	public EllipseLike(int x, int y, 
			int horizontalRadius, int verticalRadius){
		if(horizontalRadius < 1 || verticalRadius < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have radius less than 1!");
		}

		this.centerX = x;
		this.centerY = y;
		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
		
	}
	
	
	@Override
	public boolean containsPoint(int x, int y) {
		if( x < centerX - horizontalRadius){
			return false;
		}
		if(x > centerX + horizontalRadius){
			return false;
		}
		
		if( y < centerY - verticalRadius){
			return false;
		}
		if(y > centerY + verticalRadius){
			return false;
		}
		
		/*
		 * Formula from 
		 * http://math.stackexchange.com/questions/76457/check-if-a-point-is-within-an-ellipse 
		 * flipping axis for the equation if needed
		 */
		double semiMajor;
		double semiMinor;
		int checkingX;
		int checkingY;
		int centerX;
		int centerY;
		
		if(horizontalRadius >= verticalRadius){
			semiMajor = horizontalRadius;
			semiMinor = verticalRadius;
			checkingX = x;
			checkingY = y;
			centerX = this.centerX;
			centerY = this.centerY;
		} else {
			semiMajor = verticalRadius;
			semiMinor = horizontalRadius;
			checkingX = y;
			checkingY = x;
			centerX = this.centerY;
			centerY = this.centerX;
		}
		
		double result = Math.pow(checkingX - centerX, 2)/
				Math.pow(semiMajor, 2) + 
				Math.pow(checkingY - centerY, 2)/
				Math.pow(semiMinor, 2);
		
		if(result < 1){
			return true;
		} else {
			return false;
			
		}
	}
	
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.GeometricShape#draw(hr.fer.zemris.java.graphics.raster.BWRaster)
	 */
	@Override
	public void draw(BWRaster r) {
		int startX = 0;
		int startY = 0;
		
		if(centerX - horizontalRadius > startX){
			startX = centerX - horizontalRadius;
		}
		if(centerY - verticalRadius > startY){
			startY = centerY - verticalRadius;
		}
		
		int endX = r.getWidth();
		int endY = r.getHeight();
		
		if(centerX + horizontalRadius < endX){
			endX = centerX + horizontalRadius;
		}
		if(centerY + verticalRadius < endY){
			endY = centerY + verticalRadius;
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
	 * @return the X coordinate of the center
	 */
	public int getX() {
		return centerX;
	}

	
	/**
	 * @return the Y coordinate of the center
	 */
	public int getY() {
		return centerY;
	}
	
	
	/*
	 * ******** Setter methods ***************************************
	 */
	
	
	/**
	 * Sets the x cooridnate of the center.
	 * @param x the center x 
	 */
	public void setX(int x) {
		centerX = x;
	}

	
	/**
	 * Sets the y coordinate of the center.
	 * @param y the center y
	 */
	public void setY(int y) {
		centerY = y;
	}
}
