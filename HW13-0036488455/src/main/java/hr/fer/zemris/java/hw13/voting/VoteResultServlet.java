package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a sorted table of music bands and their scores from
 * votingDefinition.txt and votingResults.txt. Sets the table to "table" request
 * parameter. Sets the name of the best song to "firstName" and second best to
 * "secondName" request parameters. Sets the links to song to "firstLink" and
 * "secondLink" respectively.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class VoteResultServlet extends HttpServlet {

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

		String fileName = req.getServletContext()
				.getRealPath("/WEB-INF/votingDefinition.txt");
		Path filePath = Paths.get(fileName);

		if (!Files.exists(filePath)) {
			req.setAttribute("error", "Unable to find definition file!");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		byte[] encoded = Files.readAllBytes(Paths.get(fileName));
		String[] definitions = new String(encoded, "UTF-8").split("\n");

		StringBuilder table = new StringBuilder();
		table.append("<table border=\"2\"><tr><th>Number</th><th>Sine</th>"
				+ "<th>Cosine</th>" + "</tr>");

		for (int i = 0; i < map.size(); i++) {
			String[] entry = map.get(i).split("\t");
			if (i == 0) {
				for (String definition : definitions) {
					String[] definitionSplit = definition.split("\t");
					if (definitionSplit[1].equals(entry[0])) {
						req.setAttribute("firstName", definitionSplit[1]);
						req.setAttribute("firstLink", definitionSplit[2]);
						break;
					}
				}
			}
			if (i == 1) {
				for (String definition : definitions) {
					String[] definitionSplit = definition.split("\t");
					if (definitionSplit[1].equals(entry[0])) {
						req.setAttribute("secondName", definitionSplit[1]);
						req.setAttribute("secondLink", definitionSplit[2]);
						break;
					}
				}
			}

			table.append("<tr><td>" + (i + 1) + "." + "</td><td>" + entry[0]
					+ "</td><td>" + entry[1] + "</td></tr>");
		}
		table.append("</table>");

		req.setAttribute("table", table.toString());
		req.getRequestDispatcher("/WEB-INF/pages/votingResults.jsp")
				.forward(req, resp);
	}

}
