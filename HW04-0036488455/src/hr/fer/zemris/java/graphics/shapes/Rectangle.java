package hr.fer.zemris.java.graphics.shapes;

/**
 * A geometric shape with 4 sides, opposite sides have the 
 * same length and 4 90° angles.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Rectangle extends RectangleLike{
	
	
	/**
	 * Creates a rectangle with the given parameters.
	 * 
	 * @param x x coordinate of the upper left corner of the rectangle
	 * @param y y coordinate of the upper left cornef of the rectangle
	 * @param width width of the rectangle
	 * @param height height of the rectangle
	 */
	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	
	/*
	 * ******** Getter methods ***************************************
	 */


	/**
	 * Gets the width of the rectanle.
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}



	/**
	 * Gets the height of the rectangle.
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}


	/*
	 * ******** Setter methods ***************************************
	 */


	/**
	 * Sets the width of the rectangle.
	 * @param width the width of the rectangle
	 */
	public void setWidth(int width) {
		if(width < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have width less than 1!");
		}
		this.width = width;
	}



	/**
	 * Sets the height of the rectangle.
	 * @param height the height to rectangle
	 */
	public void setHeight(int height) {
		if(height < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have height less than 1!");
		}
		this.height = height;
	}

	
}
