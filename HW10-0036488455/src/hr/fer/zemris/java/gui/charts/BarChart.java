package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Holds information about a bar chart bar chart.
 * </p>
 * <p>
 * This class is used by {@link BarChartComponent} to store the values
 * represented by it. Given {@link XYValue} shouldn't have their y component set
 * to below the yMin or they might not be able to be represented by the classes
 * that used this class.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see BarChartComponent
 */
public class BarChart {

	/**
	 * Minimal value of the y axis.
	 */
	private int yMin;

	/**
	 * Minimal value of the x axis.
	 */
	private int yMax;

	/**
	 * Step with which to increment y axis traversal.
	 */
	private int yStep;

	/**
	 * Name of the x axis.
	 */
	private String xName;

	/**
	 * Name of the y axis.
	 */
	private String yName;

	/**
	 * Bars contained by the chart.
	 */
	private XYValue[] bars;


	/**
	 * Creates the chart with given parameters.
	 * 
	 * @param bars
	 *            bars to be represented by the chart
	 * @param xName
	 *            name of the x axis
	 * @param yName
	 *            name of the y axis
	 * @param yMin
	 *            minimal value of the y axis
	 * @param yMax
	 *            maximal value of the y axis
	 * @param yStep
	 *            y increment value
	 */
	public BarChart(List<XYValue> bars, String xName, String yName, int yMin,
			int yMax, int yStep) {
		Objects.requireNonNull(bars);
		Objects.requireNonNull(xName);
		Objects.requireNonNull(yName);
		if (yStep < 1) {
			throw new IllegalArgumentException(
					"Warning - Must step Y axis with at least 1 but was given: "
							+ yStep + "!");
		}
		if (yMax <= yMin) {
			throw new IllegalArgumentException(
					"Warning - yMax must be greater than yMin!");
		}

		this.xName = xName;
		this.yName = yName;

		this.yMin = yMin;
		this.yStep = yStep;

		int division = (yMax - yMin) / yStep;
		int remainder = (yMax - yMin) % yStep;
		if (remainder != 0) {
			division++;
		}
		this.yMax = yMin + yStep * division;

		while (bars.remove(null))
			;
		bars.sort(null);
		this.bars = bars.toArray(new XYValue[0]);
	}


	/**
	 * @return the yMin
	 */
	public int getYMin() {
		return yMin;
	}


	/**
	 * @return the yMax
	 */
	public int getYMax() {
		return yMax;
	}


	/**
	 * @return the yDiv
	 */
	public int getYStep() {
		return yStep;
	}


	/**
	 * @return the xName
	 */
	public String getXName() {
		return xName;
	}


	/**
	 * @return the yName
	 */
	public String getYName() {
		return yName;
	}


	/**
	 * @return the bars
	 */
	public XYValue[] getBars() {
		return bars;
	}

}
