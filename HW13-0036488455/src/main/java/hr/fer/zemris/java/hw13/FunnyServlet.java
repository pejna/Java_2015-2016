package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Randomizes the text color for funny.jsp.
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class FunnyServlet extends HttpServlet {

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
		Random rand = new Random();
		req.setAttribute("color", "#" + Integer.toHexString(rand.nextInt()));
		req.getRequestDispatcher("/WEB-INF/stories/funny.jsp").forward(req,
				resp);
	}
}
