package co.uk.bittwisted.repository.impl;

import co.uk.bittwisted.domain.BaseMediaInfo;
import co.uk.bittwisted.repository.ApiRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public abstract class AbstractApiRepository<T extends BaseMediaInfo> implements ApiRepository<T> {
	private Logger logger = LoggerFactory.getLogger(AbstractApiRepository.class);
	
	private String apiBaseURL;
	private HttpClient httpClient;
	private final String USER_AGENT = "Mozilla/5.0";
	
	AbstractApiRepository(String apiBaseURL) {
		this.apiBaseURL = apiBaseURL;
	}
	
	public Optional<JsonNode> doGet() throws IOException,
					IllegalStateException, URISyntaxException {
		return doGet(new HashMap<>());
	}
	
	public Optional<JsonNode> doGet(Map<String, String> params) throws IOException,
					IllegalStateException, URISyntaxException {
		StringBuilder result = new StringBuilder();
		Optional<JsonNode> optional = Optional.empty();
		
		URI uri;
		Optional<URI> uriOptional = getUrlWithParameters(params);
		if(uriOptional.isPresent()) {
			uri = uriOptional.get();
		} else {
			logger.error("URI could not be formed from set url and parameters.");
			throw new IllegalStateException("URI could not be formed from set url and parameters.");
		}
		
		if(httpClient == null) httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(uri);
		
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = httpClient.execute(request);
		
		int responseStatus = response.getStatusLine().getStatusCode();
		if(responseStatus != 200) {
			logger.error("Expected HTTP response status 200 " +
							"but instead got [" + responseStatus + "]");
			throw new IllegalStateException("Expected HTTP response status 200 " +
							"but instead got [" + responseStatus + "]");
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line;
		while((line  = br.readLine()) != null) {
			result.append(line);
		}
		
		if(!result.toString().equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(result.toString());
			optional = Optional.ofNullable(jsonNode);
		}
		
		return optional;
	}
	
	public Optional<URI> getUrlWithParameters(Map<String, String> params) throws URISyntaxException {
		URI uri = new URI(apiBaseURL);
		URIBuilder uriBuilder = new URIBuilder(uri);
		
		params.forEach(uriBuilder::setParameter);
		Optional<URI> optional = Optional.of(uriBuilder.build());
		
		return optional;
	}
	
	public void setApiBaseURL(String apiBaseURL) {
		this.apiBaseURL = apiBaseURL;
	}
	
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
}
