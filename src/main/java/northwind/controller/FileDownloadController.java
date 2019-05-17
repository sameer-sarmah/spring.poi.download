package northwind.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import northwind.writer.ExcelWriter;


@RestController
public class FileDownloadController {

	@Autowired
	ExcelWriter excelWriter;
	
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
    public void downloadTextExcel( HttpServletRequest request,HttpServletResponse response) {
    	String contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    	String webInfPath = request.getServletContext().getRealPath("/WEB-INF/classes");
    	String fileName=excelWriter.write(webInfPath,getProducts());
    	downloadFile(request,response,fileName,contentType);
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
    	String path = String.format("/WEB-INF/classes/%s",fileName);
        InputStream is = request.getServletContext().getResourceAsStream(path);
        if (is !=null)
        {
            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try
            {   ServletOutputStream out = response.getOutputStream();
                int content = is.read();
                while(content > -1) {
                	out.write(content);
                	content = is.read();
                }
                out.flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}
