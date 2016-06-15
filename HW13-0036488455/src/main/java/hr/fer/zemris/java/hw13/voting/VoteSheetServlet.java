package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Creates an xls sheet in the get request. The sheet contains data on the
 * ordering of bands in the voting competition.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class VoteSheetServlet extends HttpServlet {

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

		@SuppressWarnings("resource")
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Results");

		int rowCounter = 0;
		HSSFRow rowhead = sheet.createRow(rowCounter);
		rowCounter++;
		rowhead.createCell(0).setCellValue("Artist");
		rowhead.createCell(1).setCellValue("Result");

		for (int i = 0; i < map.size(); i++) {
			rowhead = sheet.createRow(rowCounter++);
			String[] current = map.get(i).split("\t");
			rowhead.createCell(0).setCellValue(current[0]);
			rowhead.createCell(1).setCellValue(current[1]);
		}

		resp.setContentType("application/vnd.ms-excel");
		hwb.write(resp.getOutputStream());
	}
}
