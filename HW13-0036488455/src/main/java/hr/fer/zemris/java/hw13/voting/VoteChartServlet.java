package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.util.Map;

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
 * Creates a pie chart of all music bands and their gotten results from
 * votingDefinition.txt and votingResults.txt files.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class VoteChartServlet extends HttpServlet {

	/**
	 * Serial version id of the class.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Takes care of the get request.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<Integer, String> map = VoteUtil.getResultMap(req);

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < map.size(); i++) {
			String[] current = map.get(i).split("\t");
			dataset.setValue(current[0], Integer.parseInt(current[1]));
		}

		JFreeChart chart = createChart(dataset, "Results");
		resp.setContentType("image/png");
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
		plot.setStartAngle(300);
		plot.setDirection(Rotation.ANTICLOCKWISE);
		plot.setForegroundAlpha(0.7f);
		return chart;
	}
}
