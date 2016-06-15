package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * Gets an xml file with 0 to n marked sheets. Each sheet has a list of numbers
 * from a to b and their power to the i-th potention. a, b and n are request 
 * parameters that should be given.
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class SheetServlet extends HttpServlet {

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
		int a = (int) Double.parseDouble(req.getParameter("a"));
		int b = (int) Double.parseDouble(req.getParameter("b"));
		int n = (int) Double.parseDouble(req.getParameter("n"));

		@SuppressWarnings("resource")
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("x^" + i);
			int rowCounter = 0;
			HSSFRow rowhead = sheet.createRow(rowCounter);
			rowCounter++;

			rowhead.createCell(0).setCellValue("number");
			rowhead.createCell(1).setCellValue("power");
			for (int j = a; j <= b; j++) {
				rowhead = sheet.createRow(rowCounter++);
				rowhead.createCell(0).setCellValue(j);
				rowhead.createCell(1).setCellValue(Math.pow(j, i));
			}
		}

		resp.setContentType("application/vnd.ms-excel");
		hwb.write(resp.getOutputStream());
	}
}
