package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a table of values from a to b with their sine and cosine values.
 * Table can be accesed with "table" request parameter.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class TrigonometricServlet extends HttpServlet {

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
		int a = 0;
		if (req.getParameter("a") != null) {
			a = (int) Double.parseDouble(req.getParameter("a"));
		}
		int b = 360;
		if (req.getParameter("b") != null) {
			b = (int) Double.parseDouble(req.getParameter("b"));
		}

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}

		if (b - a > 720) {
			b = a + 720;
		}

		StringBuilder table = new StringBuilder();
		table.append("<table border=\"2\"><tr><th>Number</th><th>Sine</th>"
				+ "<th>Cosine</th>" + "</tr>");

		for (int i = a; i <= b; i++) {
			table.append(
					"<tr><td>" + i + "</td><td>"
							+ String.format("%1$,.2f",
									Math.sin(i * 0.0174532925))
							+ "</td><td>" + String.format("%1$,.2f",
									Math.cos(i * 0.0174532925))
							+ "</td></tr>");
		}
		table.append("</table>");

		req.setAttribute("a", a);
		req.setAttribute("b", b);
		req.setAttribute("table", table.toString());
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp")
				.forward(req, resp);
	}
}
