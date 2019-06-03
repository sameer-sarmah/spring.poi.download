package northwind.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import northwind.writer.ExcelStreamWriter;
import northwind.writer.ExcelFileWriter;


@RestController
public class FileDownloadControllerWithStream {

	@Autowired
	ExcelFileWriter excelWriter;
	
    
    @RequestMapping( value = "/downloadexcel",method = RequestMethod.GET)
    public void downloadExcel( HttpServletRequest request,HttpServletResponse response) {
    	String contentType="application/zip";
    	try {
    		ServletOutputStream out = response.getOutputStream();
    		long timestamp =new Date().getTime();
    		String fileName="Products_"+timestamp+".xlsx";
    		String zipName="Products_"+timestamp+".zip";
    		List<Product> products = getProducts();
    		long startTime = new Date().getTime();
    		ByteArrayInputStream byteInputStream = zip(Arrays.asList(fileName),products);
	    	downloadFile(request,response,zipName,contentType,byteInputStream);
	    	long endTime = new Date().getTime();
	    	System.out.println("Time elapsed FileDownloadControllerWithStream "+ (endTime- startTime));
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
    
    private void downloadFile(HttpServletRequest request,HttpServletResponse response,String fileName,String contentType,ByteArrayInputStream byteInputStream) {
        if (byteInputStream !=null)
        {
            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            ServletOutputStream outputStream = null;
            try
            {   outputStream = response.getOutputStream();
            	IOUtils.copy(byteInputStream, outputStream);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            finally {
                try {
                	outputStream.flush();
                	outputStream.close();
	                byteInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

            }
        }
    }

	
	private static ByteArrayInputStream zip(List<String> filesToZip,List<Product> products) throws IOException {
		long timestamp =new Date().getTime();
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(bos);
		for (String fileToZip : filesToZip) {
	        ZipEntry zipEntry = new ZipEntry(fileToZip);
	        zipOut.putNextEntry(zipEntry);
	        ExcelStreamWriter.write(products, zipOut);
	       
		}
		zipOut.flush();
		zipOut.close();
		return new ByteArrayInputStream( bos.toByteArray() );
	}
	

}
