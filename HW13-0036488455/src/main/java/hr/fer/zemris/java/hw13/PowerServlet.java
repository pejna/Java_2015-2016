package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.itextpdf.text.pdf.PdfWriter;

/**
 * Manages the parameter checking for power.jsp.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see SheetServlet
 */
public class PowerServlet extends HttpServlet {

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
		int a = 0;
		if (req.getParameter("a") != null) {
			a = (int) Double.parseDouble(req.getParameter("a"));
		} else {
			req.setAttribute("error", "Aborting - Cannot read parameter a.");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		int b = 360;
		if (req.getParameter("b") != null) {
			b = (int) Double.parseDouble(req.getParameter("b"));
		} else {
			req.setAttribute("error", "Aborting - Cannot read parameter b.");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		int n = 0;
		if (req.getParameter("n") != null) {
			n = (int) Double.parseDouble(req.getParameter("n"));
		} else {
			req.setAttribute("error", "Aborting - Cannot read parameter n.");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		if (a < -100 || a > 100) {
			req.setAttribute("error",
					"Aborting - Parameter a out of bounds, must be [-100, 100] but was "
							+ a + ".");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		if (b < -100 || b > 100) {
			req.setAttribute("error",
					"Aborting - Parameter b out of bounds, must be [-100, 100] but was "
							+ b + ".");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		if (n < 1 || n > 5) {
			req.setAttribute("error",
					"Aborting - Parameter n out of bounds, must be [1, 5] but was "
							+ n + ".");
			req.getRequestDispatcher("WEB-INF/error/error.jsp").forward(req,
					resp);
			return;
		}

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}

		req.setAttribute("a", a);
		req.setAttribute("b", b);
		req.setAttribute("n", n);
		req.getRequestDispatcher("/WEB-INF/pages/power.jsp").forward(req, resp);
	}
}
