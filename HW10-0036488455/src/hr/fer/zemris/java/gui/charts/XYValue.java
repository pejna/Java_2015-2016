package hr.fer.zemris.java.gui.charts;

public class XYValue implements Comparable<XYValue> {

	private int x;

	private int y;


	public XYValue() {
	}


	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}


	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}


	@Override
	public int compareTo(XYValue o) {
		if (this.x < o.x) {
			return -1;
		} else if (this.x > o.x) {
			return 1;
		} else {
			return 0;
		}
	}
}
