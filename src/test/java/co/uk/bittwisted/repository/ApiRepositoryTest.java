package co.uk.bittwisted.repository;

import co.uk.bittwisted.repository.impl.LastFMApiRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by kvfbowden on 5/26/2017.
 */
public class ApiRepositoryTest {
	
	private final String VALID_URL = "http://www.test.com/";
	
	private final String responseSuccessBody = "{\n" +
					"  \"results\": {\n" +
					"    \"opensearch:Query\": {\n" +
					"      \"#text\": \"\",\n" +
					"      \"role\": \"request\",\n" +
					"      \"searchTerms\": \"deftones\",\n" +
					"      \"startPage\": \"1\"\n" +
					"    },\n" +
					"    \"opensearch:totalResults\": \"30584\",\n" +
					"    \"opensearch:startIndex\": \"0\",\n" +
					"    \"opensearch:itemsPerPage\": \"50\",\n" +
					"    \"@attr\": {\n" +
					"      \"for\": \"deftones\"\n" +
					"    }\n" +
					"  }\n" +
					"}";
	
	private final String malformedJsonResponse = "{\n" +
					"  \"results\": {\n" +
					"    \"opensearch:Query\": {\n" +
					"      \"#text\": \"\"\n" +
					"      \"role\": \"request\",\n" +
					"      \"searchTerms\": \"deftones\",\n" +
					"      \"startPage\": \"1\"\n" +
					"    },\n" +
					"    \"opensearch:totalResults\": \"30584\",\n" +
					"    \"opensearch:startIndex\": \"0\",\n" +
					"    \"opensearch:itemsPerPage\": \"50\",\n" +
					"    \"@attr\": {\n" +
					"}";
	
	@Test
	public void testDoGetResponseSuccessReturnsValidJSONNode() throws Exception {
		HttpResponse response = prepareResponse(200, responseSuccessBody);
		HttpClient mockClient = mock(HttpClient.class);
		Mockito.when(mockClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(response);

		LastFMApiRepository lastFMApiRepository = new LastFMApiRepository();
		lastFMApiRepository.setHttpClient(mockClient);
		lastFMApiRepository.setApiBaseURL(VALID_URL);

		Optional<JsonNode> httpResponse = lastFMApiRepository.doGet();
		assertTrue(httpResponse.isPresent());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDoGetResponseUnsuccessful() throws Exception {
		HttpResponse response = prepareResponse(400, responseSuccessBody);
		HttpClient mockClient = mock(HttpClient.class);
		Mockito.when(mockClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(response);
		
		LastFMApiRepository lastFMApiRepository = new LastFMApiRepository();
		lastFMApiRepository.setHttpClient(mockClient);
		lastFMApiRepository.setApiBaseURL(VALID_URL);
		
		lastFMApiRepository.doGet();
	}

	@Test(expected = IOException.class)
	public void testDoGetUnsuccessfulJsonParsingReturnsEmpty() throws Exception {
		HttpResponse response = prepareResponse(200, malformedJsonResponse);
		HttpClient mockClient = mock(HttpClient.class);
		Mockito.when(mockClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(response);
		
		LastFMApiRepository lastFMApiRepository = new LastFMApiRepository();
		lastFMApiRepository.setHttpClient(mockClient);
		lastFMApiRepository.setApiBaseURL(VALID_URL);
		
		lastFMApiRepository.doGet();
	}
	
	@Test
	public void testGetURLWithParameters() throws Exception {
		LinkedHashMap<String, String> params = new LinkedHashMap<>();
		params.put("test1", "value1");
		params.put("test2", "value2");
		params.put("test3", "value3");
		
		LastFMApiRepository lastFMApiRepository = new LastFMApiRepository();
		lastFMApiRepository.setApiBaseURL(VALID_URL);
		Optional<URI> optional = lastFMApiRepository.getUrlWithParameters(params);
		
		assertTrue(optional.isPresent());
		assertTrue(optional.get().toASCIIString().equals(VALID_URL + "?test1=value1&test2=value2&test3=value3"));
	}
	
	private HttpResponse prepareResponse(int expectedResponseStatus, String expectedResponseBody) {
		HttpResponse response = new BasicHttpResponse(new BasicStatusLine(
						new ProtocolVersion("HTTP", 1, 1), expectedResponseStatus, ""));
		response.setStatusCode(expectedResponseStatus);
		try {
			response.setEntity(new StringEntity(expectedResponseBody));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
		return response;
	}
}