package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Charts out the OS usage servey data as a pie chart in a png format.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class ReportChartServlet extends HttpServlet {

	/**
	 * Serial version id of the class.
	 */
	private static final long serialVersionUID = 1L;


	@Override
	/**
	 * Takes care of the get request.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// This will create the dataset
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 12);
		dataset.setValue("Mac", 60);
		dataset.setValue("Windows", 28);

		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, "Results");
		resp.setContentType("image/png");
		System.out.println("printing");
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 500, 360);
	}


	/**
	 * Creates the chart from the given data.
	 * 
	 * @param dataset
	 *            dataset for the chart
	 * @param title
	 *            title of the chart
	 * @return returns the chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true,
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
