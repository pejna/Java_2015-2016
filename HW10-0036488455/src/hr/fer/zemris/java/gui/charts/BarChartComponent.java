package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * <p>
 * {@link JComponent} able to hold information about a bar chart.
 * </p>
 * <p>
 * Enables the user to display bar charts in custom colors and bars always
 * sorted by x axis values. Information contained should be passed through the
 * constructor with {@link BarChart}. For further info on formating and input
 * look at {@link BarChart} documentation.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see BarChart
 */
public class BarChartComponent extends JComponent {

	/**
	 * Serial version of the component.
	 */
	private static final long serialVersionUID = -8850257558219543069L;

	/**
	 * Chart that his component charts.
	 */
	private BarChart chart;

	/**
	 * Name of the y axis.
	 */
	private String yName;

	/**
	 * Name of the x axis.
	 */
	private String xName;

	/**
	 * Color of the background.
	 */
	private Color backgroundColor;

	/**
	 * Color of the lines in the chart.
	 */
	private Color lineColor;

	/**
	 * Color of the bars in the chart.
	 */
	private Color barColor;

	/**
	 * Color of the bar outline in the chart.
	 */
	private Color barOutlineColor;

	/**
	 * Color of the axis lines.
	 */
	private Color axisColor;

	/**
	 * Color of the words and numbers.
	 */
	private Color characterColor;

	/**
	 * Position of the 0, 0 coordinate of the chart from the bottom left corner.
	 */
	private Dimension chartStart;

	/**
	 * Pixel height of the axis names.
	 */
	private int nameHeight;

	/**
	 * Pixel width of the number marks.
	 */
	private int numberWidth;

	/**
	 * Default background color.
	 */
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

	/**
	 * Default line color.
	 */
	private static final Color DEFAULT_LINE_COLOR = new Color(243, 232, 211);

	/**
	 * Default bar color.
	 */
	private static final Color DEFAULT_BAR_COLOR = new Color(244, 119, 72);

	/**
	 * Default bar outline color.
	 */
	private static final Color DEFAULT_BAR_OUTLINE_COLOR = Color.WHITE;

	/**
	 * Default axis color.
	 */
	private static final Color DEFAULT_AXIS_COLOR = Color.GRAY;

	/**
	 * Default words and numbers color.
	 */
	private static final Color DEFAULT_CHARACTER_COLOR = Color.BLACK;

	/**
	 * Space between chart mark and number of that mark.
	 */
	private static final int NUMBER_MARK_SPACING = 3;

	/**
	 * Length of the chart mark.
	 */
	private static final int MARK_LENGTH = 8;

	/**
	 * Space between mark number and axis name.
	 */
	private static final int NAME_NUMBER_SPACING = 10;

	/**
	 * Space between axis name and edge of component.
	 */
	private static final int NAME_EDGE_SPACING = 15;

	/**
	 * Space left for the arrows in top and right.
	 */
	private static final int ARROW_SPACING = 15;


	/**
	 * Creates the component representing the given chart.
	 * 
	 * @param chart
	 *            chart to represent
	 */
	public BarChartComponent(BarChart chart) {
		Objects.requireNonNull(chart);
		this.chart = chart;

		xName = chart.getXName();
		yName = chart.getYName();

		setBackground(DEFAULT_BACKGROUND_COLOR);

		backgroundColor = DEFAULT_BACKGROUND_COLOR;
		lineColor = DEFAULT_LINE_COLOR;
		barColor = DEFAULT_BAR_COLOR;
		barOutlineColor = DEFAULT_BAR_OUTLINE_COLOR;
		axisColor = DEFAULT_AXIS_COLOR;
		characterColor = DEFAULT_CHARACTER_COLOR;
	}


	@Override
	protected void paintComponent(Graphics g) {
		String yMax = chart.getYMax() + "";
		String yMin = chart.getYMin() + "";

		FontMetrics metrics = g.getFontMetrics();
		nameHeight = metrics.getAscent();
		numberWidth = Math.max(metrics.stringWidth(yMax),
				metrics.stringWidth(yMin));

		int chartStartDimension = nameHeight + NAME_EDGE_SPACING
				+ NAME_NUMBER_SPACING + numberWidth;
		chartStart = new Dimension(chartStartDimension, chartStartDimension);

		int stepNum = (chart.getYMax() - chart.getYMin()) / chart.getYStep();

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		int yDrawStart = getInsets().top + ARROW_SPACING + 1;
		int xDrawStart = getInsets().left + chartStart.width;

		int barNum = chart.getBars().length;
		int drawWidth = getWidth() - chartStart.width - ARROW_SPACING
				- getInsets().left - getInsets().right;
		int drawHeight = getHeight() - chartStart.height - ARROW_SPACING
				- getInsets().top - getInsets().bottom;

		paintAxisNames(g, drawWidth, drawHeight);
		paintGrid(g, stepNum, barNum, drawWidth, drawHeight, xDrawStart,
				yDrawStart);
		paintBars(g, stepNum, barNum, drawWidth, drawHeight, xDrawStart,
				yDrawStart);
		paintAxis(g, drawWidth, drawHeight, xDrawStart, yDrawStart);
	}


	/**
	 * Paints the bars of the chart.
	 * 
	 * @param g
	 *            graphich with which to paint
	 * @param stepNum
	 *            number of steps
	 * @param barNum
	 *            number of bars
	 * @param drawWidth
	 *            width of the drawable graph surface
	 * @param drawHeight
	 *            height of the drawable graph surface
	 * @param xDrawStart
	 *            leftmost coordinate of the drawable surface
	 * @param yDrawStart
	 *            topmost coordinate of the drawable surface
	 */
	private void paintBars(Graphics g, int stepNum, int barNum, int drawWidth,
			int drawHeight, int xDrawStart, int yDrawStart) {
		XYValue[] bars = chart.getBars();
		int yMin = chart.getYMin();

		int yPart = drawHeight / stepNum;
		int barWidth = drawWidth / barNum;

		int xDraw = xDrawStart;
		for (int i = 0; i < bars.length; i++) {
			int height = yPart * (bars[i].getY() - yMin) / chart.getYStep();
			int yRectCoord = getHeight() - chartStart.height
					- getInsets().bottom - height;

			g.setColor(barColor);
			g.fillRect(xDraw, yRectCoord, barWidth, height);

			g.setColor(barOutlineColor);
			g.drawRect(xDraw, yRectCoord, barWidth, height);

			g.setColor(characterColor);
			String number = "" + bars[i].getX();
			int numberWidth = g.getFontMetrics().stringWidth(number);
			g.drawString(number, xDraw + barWidth / 2 - numberWidth / 2,
					getHeight() - chartStart.height + MARK_LENGTH
							+ NUMBER_MARK_SPACING + nameHeight);
			xDraw += barWidth;
		}
	}


	/**
	 * Draws the back grid.
	 * 
	 * @param g
	 *            graphich with which to paint
	 * @param stepNum
	 *            number of steps
	 * @param barNum
	 *            number of bars
	 * @param drawWidth
	 *            width of the drawable graph surface
	 * @param drawHeight
	 *            height of the drawable graph surface
	 * @param xDrawStart
	 *            leftmost coordinate of the drawable surface
	 * @param yDrawStart
	 *            topmost coordinate of the drawable surface
	 */
	private void paintGrid(Graphics g, int stepNum, int barNum, int drawWidth,
			int drawHeight, int xDrawStart, int yDrawStart) {

		int yValue = chart.getYMax();
		int yStep = chart.getYStep();

		int yDraw = yDrawStart;
		for (int i = 0; i < stepNum; i++) {
			// draws grid line
			g.setColor(lineColor);
			g.drawLine(xDrawStart, yDraw, getWidth() - getInsets().right,
					yDraw);
			g.drawLine(xDrawStart, yDraw + 1, getWidth() - getInsets().right,
					yDraw + 1);

			// draws axis markings
			g.setColor(axisColor);
			g.drawLine(xDrawStart - MARK_LENGTH, yDraw, xDrawStart, yDraw);
			g.drawLine(xDrawStart - MARK_LENGTH, yDraw + 1, xDrawStart,
					yDraw + 1);

			// draws axis numbers
			String yString = "" + yValue;
			int currentWidth = g.getFontMetrics().stringWidth(yString);
			g.setColor(characterColor);
			g.drawString(yString, xDrawStart - MARK_LENGTH - NUMBER_MARK_SPACING
					- currentWidth, yDraw + nameHeight / 2 - 1);

			yValue -= yStep;
			yDraw += drawHeight / stepNum;
		}

		// draws zero
		String yString = "" + yValue;
		int currentWidth = g.getFontMetrics().stringWidth(yString);
		g.setColor(characterColor);
		g.drawString(yString,
				xDrawStart - MARK_LENGTH - NUMBER_MARK_SPACING - currentWidth,
				yDraw + nameHeight / 2 - 1);

		int xDraw = xDrawStart + drawWidth / barNum;
		for (int i = 0; i < barNum; i++) {
			g.setColor(lineColor);
			g.drawLine(xDraw, yDrawStart - ARROW_SPACING, xDraw,
					getHeight() - chartStart.height - getInsets().bottom);
			g.drawLine(xDraw + 1, yDrawStart - ARROW_SPACING, xDraw + 1,
					getHeight() - chartStart.height - getInsets().bottom);

			g.setColor(axisColor);
			g.drawLine(xDraw, yDraw, xDraw, yDraw + MARK_LENGTH);
			g.drawLine(xDraw + 1, yDraw, xDraw + 1, yDraw + MARK_LENGTH);

			xDraw += drawWidth / barNum;
		}

	}


	/**
	 * Draws the axis.
	 * 
	 * @param g
	 *            graphics with which to paint
	 * @param drawWidth
	 *            width of the drawable graph surface
	 * @param drawHeight
	 *            height of the drawable graph surface
	 * @param xDrawStart
	 *            leftmost coordinate of the drawable surface
	 * @param yDrawStart
	 *            topmost coordinate of the drawable surface
	 */
	private void paintAxis(Graphics g, int drawWidth, int drawHeight,
			int xDrawStart, int yDrawStart) {
		int yDraw = drawHeight + ARROW_SPACING + getInsets().top;

		g.setColor(axisColor);

		g.drawLine(xDrawStart - MARK_LENGTH, yDraw,
				getWidth() - getInsets().right - ARROW_SPACING / 2, yDraw);
		g.drawLine(xDrawStart - MARK_LENGTH, yDraw + 1,
				getWidth() - getInsets().right - ARROW_SPACING / 2, yDraw + 1);

		g.drawLine(xDrawStart, yDrawStart - ARROW_SPACING / 2, xDrawStart,
				yDraw + MARK_LENGTH);
		g.drawLine(xDrawStart + 1, yDrawStart - ARROW_SPACING / 2,
				xDrawStart + 1, yDraw + MARK_LENGTH);

		String arrowUp = "^";
		int arrowWidth = g.getFontMetrics().stringWidth(arrowUp);
		g.drawString(arrowUp, xDrawStart - arrowWidth / 2, yDrawStart - 1);
		g.drawString(arrowUp, xDrawStart - arrowWidth / 2 + 1, yDrawStart - 1);

		String arrowRight = ">";
		int arrowHeight = g.getFontMetrics().getAscent();
		g.drawString(arrowRight, xDrawStart + drawWidth + ARROW_SPACING / 2,
				yDraw + arrowHeight / 2 - 1);
		g.drawString(arrowRight, xDrawStart + drawWidth + ARROW_SPACING / 2,
				yDraw + arrowHeight / 2);
	}


	/**
	 * Draws the axis names.
	 * 
	 * @param g
	 *            graphics with which to paint
	 * @param drawWidth
	 *            width of the drawable graph surface
	 * @param drawHeight
	 *            height of the drawable graph surface
	 */
	private void paintAxisNames(Graphics g, int drawWidth, int drawHeight) {
		int xNameWidth = g.getFontMetrics().stringWidth(xName);
		int yNameWidth = g.getFontMetrics().stringWidth(yName);

		g.setColor(characterColor);
		g.drawString(xName, chartStart.width + drawWidth / 2 - xNameWidth / 2,
				getHeight() - getInsets().bottom - NAME_EDGE_SPACING);

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		g.drawString(yName, -(drawHeight / 2 + yNameWidth),
				NAME_EDGE_SPACING + nameHeight / 2);

		g2d.setTransform(defaultAt);
	}


	/*
	 * ******** Setters ********************************************************
	 */

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}


	/**
	 * @param lineColor
	 *            the lineColor to set
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}


	/**
	 * @param barColor
	 *            the barColor to set
	 */
	public void setBarColor(Color barColor) {
		this.barColor = barColor;
	}


	/**
	 * @param barOutlineColor
	 *            the barOutlineColor to set
	 */
	public void setBarOutlineColor(Color barOutlineColor) {
		this.barOutlineColor = barOutlineColor;
	}


	/**
	 * @param axisColor
	 *            the chartColor to set
	 */
	public void setAxisColor(Color axisColor) {
		this.axisColor = axisColor;
	}


	/**
	 * @param characterColor
	 *            the characterColor to set
	 */
	public void setCharacterColor(Color characterColor) {
		this.characterColor = characterColor;
	}

}
