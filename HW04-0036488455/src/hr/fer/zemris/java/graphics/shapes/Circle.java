package hr.fer.zemris.java.graphics.shapes;


/**
 * Type of {@link Ellipse} that has equals horizontal and vertical
 * radii.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class Circle extends EllipseLike {

	
	/**
	 * Creates a circle at given coordinates with given radius.
	 * Throws {@link IllegalArgumentException} if radius is less 
	 * than 1.
	 * 
	 * @param x x coordinate of the center of the circle
	 * @param y y coordinate of the center of the circle
	 * @param radius radisu of the circle
	 */
	public Circle(int x, int y, int radius) {
		super(x, y, radius, radius);
	}

	
	/**
	 * Gets the radius of the circle.
	 * @return returns the radius
	 */
	public int getRadius(){
		return this.horizontalRadius;
	}

	
	
	/**
	 * Sets the radius of the circle. Throws 
	 * {@link IllegalArgumentException} if given less than 1.
	 * 
	 * @param radius returns the radius of the circle
	 */
	public void setRadius(int radius){
		if(radius > 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have radius smaller than 1!");
		}
		this.horizontalRadius = radius;
		this.verticalRadius = radius;
		
	}
}
