package northwind.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import northwind.model.Product;

@Component()
public class ExcelFileWriter {

	public String write(String webInfPath,List<Product> products) {
		Workbook workbook = new XSSFWorkbook();
		long timestamp =new Date().getTime();
		String fileName="Products_"+timestamp+".xlsx";
		String filePath =webInfPath+ File.separator+fileName;
		
		Sheet sheet = workbook.createSheet("Products");
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);

		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("ProductID");
		headerCell.setCellStyle(headerStyle);
		

		headerCell = header.createCell(1);
		headerCell.setCellValue("ProductName");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(2);
		headerCell.setCellValue("QuantityPerUnit");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(3);
		headerCell.setCellValue("UnitPrice");
		headerCell.setCellStyle(headerStyle);

		CellStyle textStyle = workbook.createCellStyle();
		textStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		textStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont textFont = ((XSSFWorkbook) workbook).createFont();
		textFont.setFontName("Arial");
		textFont.setFontHeightInPoints((short) 14);
		textFont.setBold(false);
		headerStyle.setFont(textFont);
		int index;
		for (index = 1; index <= products.size(); index++) {
			Row row = sheet.createRow(index);
			createRow(products.get(index - 1), textStyle, row);
		}
		
		double totalPrice = products.stream().mapToDouble(product -> product.getUnitPriceAsDouble()).sum();
		Row row = sheet.createRow(index);
		createAggregateRow(totalPrice, textStyle, row);
		
		
		try {
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			System.out.println(file.getAbsolutePath()+" written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;

	}

	private void createRow(Product product,CellStyle cellStyle, Row row) {
		Cell rowCell = row.createCell(0);
		rowCell.setCellValue(product.getProductID());
		rowCell.setCellStyle(cellStyle);

		rowCell = row.createCell(1);
		rowCell.setCellValue(product.getProductName());
		rowCell.setCellStyle(cellStyle);

		rowCell = row.createCell(2);
		rowCell.setCellValue(product.getQuantityPerUnit());
		rowCell.setCellStyle(cellStyle);

		rowCell = row.createCell(3);
		rowCell.setCellValue(product.getUnitPrice());
		rowCell.setCellStyle(cellStyle);
	}
	
	private void createAggregateRow(double totalPrice, CellStyle cellStyle, Row row) {
		Cell rowCell = row.createCell(0);
		rowCell.setCellValue("");
		rowCell.setCellStyle(cellStyle);

		rowCell = row.createCell(1);
		rowCell.setCellValue("");
		rowCell.setCellStyle(cellStyle);

		rowCell = row.createCell(2);
		rowCell.setCellValue("");
		rowCell.setCellStyle(cellStyle);

		rowCell = row.createCell(3);
		rowCell.setCellValue(totalPrice);
		rowCell.setCellStyle(cellStyle);
	}

}
