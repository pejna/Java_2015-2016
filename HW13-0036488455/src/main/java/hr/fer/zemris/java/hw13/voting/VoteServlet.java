package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
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
 * Votes for the band in the votingResults.txt file with the id passed in the
 * request parameter "id". 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class VoteServlet extends HttpServlet {

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
		if (req.getParameter("id") == null) {
			req.setAttribute("error", "Cannot vote for no-one!");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		int choice = Integer.parseInt(req.getParameter("id"));
		String resultFileName = req.getServletContext()
				.getRealPath("/WEB-INF/votingResults.txt");

		Path resultFilePath = Paths.get(resultFileName);

		String[] results = VoteUtil.getResults(resultFileName, req);

		for (int i = 0; i < results.length; i++) {
			System.out.println("rezultat" + results[i]);
			String[] current = results[i].split("\t");
			try {
				if (Integer.parseInt(current[0].trim()) == choice) {
					current[1] = new String(
							"" + (1 + Integer.parseInt(current[1].trim())));
					results[i] = current[0].trim() + "\t" + current[1].trim();
					break;
				}
			} catch (Exception e) {
				req.setAttribute("error", "Invalid result database format, "
						+ "please try voting later!" + e.getMessage());
				req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
						resp);
				return;
			}
		}

		List<String> lineList = new ArrayList<>();
		for (String s : results) {
			lineList.add(s);
		}

		Files.write(resultFilePath, lineList, Charset.forName("UTF-8"));

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
