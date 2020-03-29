package northwind.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import northwind.client.ApacheHttpClient;
import northwind.exception.CoreException;
import northwind.model.Product;
import northwind.util.HttpMethod;
import northwind.writer.ExcelFileWriter;


@RestController
public class FileDownloadControllerWithFile {

	@Autowired
	ExcelFileWriter excelWriter;
	
    @RequestMapping( value = "/downloadpdf",method = RequestMethod.GET)
    public void downloadPDFFile( HttpServletRequest request,HttpServletResponse response) {
    	String fileName="CIPA.pdf";
    	String contentType="application/pdf";
    	downloadFile(request,response,fileName,contentType);
    }

    @RequestMapping( value = "/downloadtext",method = RequestMethod.GET)
    public void downloadTextFile( HttpServletRequest request,HttpServletResponse response) {
    	String fileName="text.txt";
    	String contentType="text/plain";
    	downloadFile(request,response,fileName,contentType);
    }
    
    @RequestMapping( value = "/downloadxls",method = RequestMethod.GET)
    public void downloadExcel( HttpServletRequest request,HttpServletResponse response) {
    	//String contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    	String contentType="application/zip";
    	String webInfPath = request.getServletContext().getRealPath("/");
		List<Product> products = getProducts();
		long startTime = new Date().getTime();
    	String fileName=excelWriter.write(webInfPath,products);
    	try {
			String zipName = zip(webInfPath,Arrays.asList(fileName));
	    	downloadFile(request,response,zipName,contentType);
	    	long endTime = new Date().getTime();
	    	System.out.println("Time elapsed FileDownloadControllerWithFile "+ (endTime- startTime));
	    	String filePath =webInfPath+ File.separator+fileName;
	    	File excelFile = new File(filePath);
	    	if(excelFile.exists()) {
	    		boolean isDeleted = excelFile.delete();
	    		System.out.println("excel file deleted " + isDeleted);
	    	}
	    	
	    	File zipfile = new File(zipName);
	    	if(zipfile.exists()) {
	    		boolean isDeleted = zipfile.delete();
	    		System.out.println("zip file deleted " + isDeleted);
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}

    	
    }
    
	public List<Product> getProducts() {
		String url = "https://services.odata.org/Northwind/Northwind.svc/Products";
		String jsonResponse = "";
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("$format", "json");
		queryParams.put("$filter", "CategoryID eq 1");
		try {
			ApacheHttpClient httpClient = new ApacheHttpClient();
			Type typeToken = new TypeToken<List<Product>>() {
			}.getType();
			jsonResponse = httpClient.request(url, HttpMethod.GET, Collections.<String, String>emptyMap(), queryParams,
					null);
			JsonObject response = new Gson().fromJson(jsonResponse, JsonObject.class);
			jsonResponse = response.get("value").toString();
			return new Gson().fromJson(jsonResponse, typeToken);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    private void downloadFile(HttpServletRequest request,HttpServletResponse response,String fileName,String contentType) {
    	String path = String.format("/%s",fileName);
        InputStream inputStream = request.getServletContext().getResourceAsStream(path);
        if (inputStream !=null)
        {
            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            ServletOutputStream outputStream = null;
            try
            {   outputStream = response.getOutputStream();
            	IOUtils.copy(inputStream, outputStream);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            finally {
                try {
                	outputStream.flush();
                	outputStream.close();
	                inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

            }
        }
    }

	
	private static String zip(String contextPath,List<String> filesToZip) throws IOException {
		long timestamp =new Date().getTime();
		String zipName="Products_"+timestamp+".zip";
		String zipPath = contextPath+ File.separator+zipName;
		FileOutputStream fos = new FileOutputStream(zipPath);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		for (String fileToZip : filesToZip) {
			String filePath = contextPath+ File.separator + fileToZip;
	        File zip = new File(filePath);
	        FileInputStream fis = new FileInputStream(filePath);
	        ZipEntry zipEntry = new ZipEntry(zip.getName());
	        zipOut.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;
	        while((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	        }
	        fis.close();
		}
		zipOut.close();
        fos.close();
		return zipName;
	}
	
}
