package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.listeners.StartupTimeContextListener;

/**
 * Measures how much time has passed since server inception. Should be used with
 * {@link StartupTimeContextListener}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see StartupTimeContextListener
 */
public class InfoServlet extends HttpServlet {

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
		long startTime = (long) getServletContext()
				.getAttribute(StartupTimeContextListener.TIME_KEYWORD);
		long timePassed = System.currentTimeMillis() - startTime;

		long seconds = (timePassed / 1000) % 60;
		long minutes = (timePassed / (1000 * 60)) % 60;
		long hours = (timePassed / (1000 * 60 * 60)) % 24;
		long days = (timePassed / (1000 * 60 * 60 * 24));

		StringBuilder sb = new StringBuilder();
		sb.append("Time passed since server startup: ");
		boolean somethingBefore = false;
		if (days > 0) {
			sb.append(days + " day");
			if (days > 1) {
				sb.append("s");
			}
			sb.append(" ");
			somethingBefore = true;
		}
		if (hours > 0) {
			sb.append(hours + " hour");
			if (hours > 1) {
				sb.append("s");
			}
			sb.append(" ");
			somethingBefore = true;
		}
		if (minutes > 0) {
			sb.append(minutes + " minute");
			if (minutes > 1) {
				sb.append("s");
			}
			sb.append(" ");
			somethingBefore = true;
		}
		if (somethingBefore) {
			sb.append(" and ");
		}

		if (seconds > 0) {
			sb.append(seconds + " second");
			if (seconds > 1) {
				sb.append("s");
			}
			somethingBefore = true;
		}

		if (!somethingBefore) {
			sb.append("not time at all!");
		} else {
			sb.append("!");
		}

		getServletContext().setAttribute("text", sb.toString());
		req.getRequestDispatcher("/WEB-INF/pages/appinfo.jsp").forward(req,
				resp);
	}
}
