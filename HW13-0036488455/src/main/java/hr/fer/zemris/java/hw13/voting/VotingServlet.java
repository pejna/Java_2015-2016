package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Procures the voting participants definitions from the votingDefinition.txt
 * file. Creates an iterable list and places it in the "idlist" request
 * attribute.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class VotingServlet extends HttpServlet {

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

		List<Integer> ids = new ArrayList<>();
		for (String definition : definitions) {
			try {
				String[] parts = definition.split("\t");
				ids.add(Integer.parseInt(parts[0]));
				req.setAttribute(parts[0], parts[1]);
			} catch (Exception e) {
				req.setAttribute("error",
						"Incorrect database format, please try again later this week!");
				req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
						resp);
				return;
			}
		}
		ids.sort(null);
		req.setAttribute("idlist", ids);

		req.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(req,
				resp);
	}

}
