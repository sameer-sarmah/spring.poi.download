package ssl.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ssl.client.ApacheClient;
import ssl.exception.CoreException;


@RestController
public class FileDownloadControllerWithStream {
	
	@Value("${download.service.url}")
    private String  downloadExcelServiceUrl;
	
	@Autowired
	private ApacheClient client;
	
	private static final String CONTENT_DISPOSITION ="Content-Disposition";
	
    @RequestMapping( value = "/downloadexcel",method = RequestMethod.GET)
    public void downloadExcel( HttpServletRequest request,HttpServletResponse response) {

    	OutputStream servletOutputStream = null;
    	try {
    		servletOutputStream = response.getOutputStream();
    		HttpResponse responseFromDownloadService =client.request(downloadExcelServiceUrl, HttpGet.METHOD_NAME, new HashMap<String,String>(), new HashMap<String,String>(), null);
    		HttpEntity responseEntity = responseFromDownloadService.getEntity();		
    		Header contentDisposition = responseFromDownloadService.getFirstHeader(CONTENT_DISPOSITION);
    		if(contentDisposition.getValue().contains("filename=")) {
    			String[] segments = contentDisposition.getValue().split("filename="); 
    			String fileName = segments[segments.length -1];
    			IOUtils.copy(responseEntity.getContent(), servletOutputStream);
        		response.setContentType(responseEntity.getContentType().getValue());
        		response.addHeader("Content-Disposition", "attachment; filename="+fileName);
    		}	
    		
		}  catch (CoreException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	finally {
    		if(servletOutputStream != null && !response.isCommitted()) {
    			try {
					servletOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}

    	
    }
    

	

}
