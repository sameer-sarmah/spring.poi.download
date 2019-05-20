package northwind.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import northwind.controller.FileDownloadController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest({FileDownloadController.class})
public class FileDownloadControllerTest {

//    @Autowired
//    private RestTemplate restTemplate;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private FileDownloadController fileDownloadController;
    
    @Test
    public void downloadTextExcelTest() {
    	try {
			MvcResult response = mockMvc.perform(get("/downloadxls")).andReturn();
			byte[] bytes = response.getResponse().getContentAsByteArray();
			long timestamp =new Date().getTime();
			String fileName="Products_"+timestamp+".xlsx";
			File file = new File(fileName);
			FileOutputStream out = new FileOutputStream(file);
			out.write(bytes);
			out.flush();
			out.close();
			FileInputStream inStream = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet("Products");
			int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for(int rowIndex = 1 ;rowIndex <= totalRows ;rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				//int totalColumns =row.getLastCellNum() - row.getFirstCellNum();
				for(int colIndex=row.getFirstCellNum();colIndex <=row.getLastCellNum();colIndex++ ) {
					Cell cell = row.getCell(colIndex);
					if(cell != null) {
						if(cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
							Assert.assertNotNull(cell.getNumericCellValue());
						}
						else if(cell.getCellTypeEnum().equals(CellType.STRING)) {
							Assert.assertNotNull(cell.getStringCellValue());
						}
					}

				}
				
			}
			inStream.close();
			workbook.close();
	    	if(file.exists()) {
	    		boolean isDeleted = file.delete();
	    		System.out.println(isDeleted);
	    	}
			

		} catch (Exception e) {
			e.printStackTrace();
			 Assert.fail(e.getMessage());
		}
    }
	
}
