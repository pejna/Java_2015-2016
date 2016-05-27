package hr.fer.zemris.java.graphics.shapes;


/**
 * A geometric shape with 4 sides of equal length and 90° angles.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Square extends RectangleLike {

	
	/**
	 * Creates a square with the given parameters.
	 * 
	 * @param x x coordinate of the upper left corner of the square
	 * @param y y coordinate of the upper left corner of the square
	 * @param size size of the sides of the square
	 */
	public Square(int x, int y, int size) {
		super(x, y, size, size);
	}

	
	/*
	 * ******** Getter methods ***************************************
	 */
	
	
	/**
	 * Gets the size of the rectangle.
	 * 
	 * @return reutrns the size of the rectangle
	 */
	public int getSize(){
		return width;
	}

	
	/*
	 * ******** Setter methods ***************************************
	 */
	
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.Rectangle#setX(int)
	 */
	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		super.setX(x);
	}

	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.Rectangle#setY(int)
	 */
	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		super.setY(y);
	}

	
	/**
	 * Sets the width and height of the square to the given size.
	 * 
	 * @param size new size of the width and height
	 */
	public void setSize(int size){
		if(width < 1){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot have size less than 1!");
		}
		
		width = size;
		height = size;
	}
}
