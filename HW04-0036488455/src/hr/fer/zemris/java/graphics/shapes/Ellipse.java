package hr.fer.zemris.java.graphics.shapes;

/**
 * Geometric shape that represents an ellipse.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Ellipse extends EllipseLike {

	
	/**
	 * Creates an ellipse with the given parameters.
	 * 
	 * @param x x coordinate of the center of ellipse
	 * @param y y coordinate of the center of ellipse
	 * @param horizontalRadius horizontal radius of the ellipse
	 * @param verticalRadius vertical radius of the ellipse
	 */
	public Ellipse(int x, int y, int horizontalRadius,
			int verticalRadius) {
		super(x, y, horizontalRadius, verticalRadius);
	}


	/*
	 * ******** Getter methods ***************************************
	 */

	
	/**
	 * @return the horizontal radius
	 */
	public int getHorizontalRadius() {
		return horizontalRadius;
	}

	
	/**
	 * @return the vertical radius
	 */
	public int getVerticalRadius() {
		return verticalRadius;
	}

	
	/*
	 * ******** Setter methods ***************************************
	 */

	
	/**
	 * Sets the horizontal radius.
	 * @param horizontalRadius the horizontal radius
	 */
	public void setHorizontalRadius(int horizontalRadius) {
		if(horizontalRadius > 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have radius smaller than 1!");
		}
		this.horizontalRadius = horizontalRadius;
	}

	
	/**
	 * Sets the verzical radius.
	 * @param verticalRadius the vertical radius
	 */
	public void setVerticalRadius(int verticalRadius) {
		if(verticalRadius > 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have radius smaller than 1!");
		}
		this.verticalRadius = verticalRadius;
	}

}
