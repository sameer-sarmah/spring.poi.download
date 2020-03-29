package northwind.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

import northwind.controller.FileDownloadControllerWithFile;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest({FileDownloadControllerWithFile.class})
public class FileDownloadControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private FileDownloadControllerWithFile fileDownloadController;
    
    @Test
    public void downloadTextExcelTest() {
    	try {
			MvcResult response = mockMvc.perform(get("/downloadxls")).andReturn();
			byte[] bytes = response.getResponse().getContentAsByteArray();
			String zipfileName="Products.zip";
			File zipfile = new File(zipfileName);
			FileOutputStream out = new FileOutputStream(zipfile);
			out.write(bytes);
			out.flush();
			out.close();
			unzip(".",zipfileName);
			File cwd = new File(".");
			String[] files = cwd.list();
			String excelfileName=null;
			for(String file:files) {
				if(file.contains(".xlsx")) {
					excelfileName = file;
				}
			}
			
			FileInputStream inStream = new FileInputStream(excelfileName);
			File excelFile = new File(excelfileName);
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheet("Products");
			int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for(int rowIndex = 1 ;rowIndex <= totalRows ;rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				//int totalColumns =row.getLastCellNum() - row.getFirstCellNum();
				for(int colIndex=row.getFirstCellNum();colIndex <=row.getLastCellNum();colIndex++ ) {
					Cell cell = row.getCell(colIndex);
					if(cell != null) {
						if(cell.getCellType().equals(CellType.NUMERIC)) {
							double cellValue = cell.getNumericCellValue();
							System.out.println(cellValue);
							Assert.assertNotNull(cellValue);
						}
						else if(cell.getCellType().equals(CellType.STRING)) {
							String cellValue = cell.getStringCellValue();
							System.out.println(cellValue);
							Assert.assertNotNull(cellValue);
						}
					}

				}
				
			}
			inStream.close();
			workbook.close();
	    	if(zipfile.exists()) {
	    		boolean isDeleted = zipfile.delete();
	    		System.out.println(isDeleted);
	    	}
	    	if(excelFile.exists()) {
	    		boolean isDeleted = excelFile.delete();
	    		System.out.println(isDeleted);
	    	}
			

		} catch (Exception e) {
			e.printStackTrace();
			 Assert.fail(e.getMessage());
		}
    }
	
	private static void unzip(String unzipDir,String zipName) throws IOException {
        File destDir = new File(unzipDir);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipName));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = unzipFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
	}
	
    public static File unzipFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        System.out.println(destFile.getName()+" unzipped");
        return destFile;
    }
}
