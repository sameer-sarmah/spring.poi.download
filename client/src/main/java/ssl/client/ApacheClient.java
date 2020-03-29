package ssl.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ssl.exception.CoreException;
import ssl.util.KeyStoreUtil;

@Component
public class ApacheClient {

	@Value("${server.ssl.key-store}")
	private String keystoreFile;
	@Value("${server.ssl.key-store-password}")
	private String keystorePwd;
	@Value("${server.ssl.key-password}")
	private String keyPwd;
	@Value("${server.ssl.key-store-type}")
	private String keyStoreType;
	@Autowired
	private KeyStoreUtil keyStoreUtil;

	final static Logger logger = Logger.getLogger(ApacheClient.class);

	public HttpResponse request(final String url, final String method, Map<String, String> headers,
			Map<String, String> queryParams, final String jsonString) throws CoreException {

		try {
			KeyStore keystore = keyStoreUtil.readStore();
			SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keystore, keyPwd.toCharArray())
					.loadTrustMaterial(keystore, new TrustSelfSignedStrategy()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					NoopHostnameVerifier.INSTANCE);

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
			BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
					socketFactoryRegistry);
			RequestBuilder requestBuilder = RequestBuilder.create(method);
			if (headers != null) {
				headers.forEach((key, value) -> {
					requestBuilder.addHeader(key, value);
				});
			}
			if (queryParams != null) {
				queryParams.forEach((key, value) -> {
					NameValuePair pair = new BasicNameValuePair(key, value);
					requestBuilder.addParameters(pair);
				});
			}

			requestBuilder.setUri(url);

			if (method.equals(HttpPost.METHOD_NAME) || method.equals(HttpPut.METHOD_NAME)) {
				StringEntity input = new StringEntity(jsonString);
				input.setContentType("application/json");
				requestBuilder.setEntity(input);
			}

			HttpUriRequest request = requestBuilder.build();
			CloseableHttpClient client = HttpClientBuilder.create()
											.setConnectionManager(connectionManager)
											.setSSLSocketFactory(sslsf)
											.setSSLContext(sslContext)
											.build();
			HttpResponse response = client.execute(request);

			return response;

		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new CoreException(e.getMessage(), 500);

		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new CoreException(e.getMessage(), 500);
		} catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new CoreException(e.getMessage(), 500);
		} catch (Exception e) {
			throw new CoreException(e.getMessage(), 500);
		}
	}
}
