package ssl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DownloadZipService {

	@Value("${download.service.url}")
    private String  downloadExcelServiceUrl;
	
	
	
}
