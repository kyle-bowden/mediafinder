package co.uk.bittwisted.repository;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Created by kvfbowden on 5/26/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractApiRepositoryTest {
	
	@Test
	public void testGetURLWithParameters() {
		String testURL = "http://www.test.com/";
		
		LinkedHashMap<String, String> params = new LinkedHashMap<>();
		params.put("test1", "value1");
		params.put("test2", "value2");
		params.put("test3", "value3");
		
		LastFMApiRepository lastFMApiRepository = new LastFMApiRepository();
		lastFMApiRepository.setApiBaseURL(testURL);
		Optional<URI> optional = lastFMApiRepository.getUrlWithParameters(params);
		
		assertTrue(optional.isPresent());
		assertTrue(optional.get().toASCIIString().equals(testURL + "?test1=value1&test2=value2&test3=value3"));
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