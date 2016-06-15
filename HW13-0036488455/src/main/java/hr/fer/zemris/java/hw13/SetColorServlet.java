package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets the background color for the whole session. Set color should be
 * requested with "color" session attribute.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class SetColorServlet extends HttpServlet {

	/**
	 * Seral version id of the class.
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameterMap().containsKey("color")) {
			if (req.getParameter("color").equals("white")) {
				req.getSession().setAttribute("color", "WHITE");

			} else if (req.getParameter("color").equals("red")) {
				req.getSession().setAttribute("color", "RED");

			} else if (req.getParameter("color").equals("green")) {
				req.getSession().setAttribute("color", "GREEN");

			} else if (req.getParameter("color").equals("cyan")) {
				req.getSession().setAttribute("color", "CYAN");
			}
		}

		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req,
				resp);
	}
}
