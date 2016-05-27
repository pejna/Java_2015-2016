package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * <p>
 * A demonstration of usage of {@link BarChart} and {@link BarChartComponent}
 * classes.
 * </p>
 * <p>
 * Requires 1 command line argument, a path to a config file. The file contains
 * information to be preocessed for the chart.
 * </p>
 * <p>
 * First line of the file holds the name of the x axis. Second line holds the
 * name of the y axis. Third line holds bar value pairs separateb by single
 * spaces: examle 1,3 4,20. Fourth line holds minimal y axis value. Fifth line
 * holds maximal y axis value. Sixth line holds the incremen of the y axis.
 * </p>
 * <p>
 * No y parts of the bar values should be above maximum y or below min y,
 * because they cannot be represented faithfully. Keep in mind that the
 * {@link BarChart} is self sorting so all bars will be sorted by the x value.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see BarChart
 * @see BarChartComponent
 */
public class BarChartDemo extends JFrame {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = 5151434034409506855L;


	/**
	 * Chart used by the component.
	 */
	private static BarChartComponent chart;

	/**
	 * Title of the frame.
	 */
	private static final String TITLE = "BarChartDemo";

	/**
	 * Path to the file.
	 */
	private static String fileName;


	/**
	 * Creates the demo class.
	 */
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(TITLE);
		setSize(600, 400);
		initGUI();
	}


	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel pathLabel = new JLabel("<html><h3>" + fileName + "</h3></html>");
		pathLabel.setHorizontalAlignment(JLabel.CENTER);
		pathLabel.setVerticalAlignment(JLabel.CENTER);
		pathLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		panel.add(pathLabel, BorderLayout.NORTH);

		panel.add(chart, BorderLayout.CENTER);
		setContentPane(panel);
	}


	/**
	 * Executes the program.
	 * 
	 * @param args
	 *            path to the decribing file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Aborting - No path given to the input file!");
			System.exit(-1);
		}

		Path path = Paths.get(args[0]);
		fileName = path.normalize().toAbsolutePath().toString();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(path.toFile()))))) {
			String xName = reader.readLine();
			String yName = reader.readLine();

			String[] points = reader.readLine().trim().split(" ");
			List<XYValue> pointList = new ArrayList<>();
			for (String point : points) {
				String[] coordinates = point.split(",");
				pointList.add(new XYValue(Integer.parseInt(coordinates[0]),
						Integer.parseInt(coordinates[1])));
			}

			int lowest = Integer.parseInt(reader.readLine().trim());
			int highest = Integer.parseInt(reader.readLine().trim());
			int step = Integer.parseInt(reader.readLine().trim());

			chart = new BarChartComponent(new BarChart(pointList, xName, yName,
					lowest, highest, step));

			SwingUtilities
					.invokeLater(() -> new BarChartDemo().setVisible(true));

		} catch (FileNotFoundException e) {
			System.err.println(
					"Aborting - Couldn't open a file on the given path!");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(
					"Aborting - Error happened while reading from the file!");
			System.exit(-1);
		}

	}
}
