package utility;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelCreator {
	public static void main(String[] args) {
		try {
			String filename = "d:/NewExcelFile.xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);

			rowhead.createCell((short) 0).setCellValue("No.");
			rowhead.createCell((short) 1).setCellValue("Name");
			rowhead.createCell((short) 2).setCellValue("Address");
			rowhead.createCell((short) 3).setCellValue("Email");

			HSSFRow row = sheet.createRow((short) 1);
			row.createCell((short) 0).setCellValue("1");
			row.createCell((short) 1).setCellValue("Sankumarsingh");
			row.createCell((short) 2).setCellValue("India");
			row.createCell((short) 3).setCellValue("sankumarsingh@gmail.com");

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
}