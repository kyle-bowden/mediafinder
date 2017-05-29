package co.uk.bittwisted.repository;

import co.uk.bittwisted.domain.AlbumInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kvfbowden on 5/29/2017.
 */
public class LastFMApiRepositoryTest {
	
	@Test
	public void search() throws Exception {
		List<AlbumInfo> albumInfoList = mockRepository.search("");
		
		assertNotNull(albumInfoList);
		assertEquals(albumInfoList.size(), 2);
	}
	
	private LastFMApiRepository mockRepository = new LastFMApiRepository() {
		private final String lastFMSearchResponse = "{\n" +
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
						"    \"albummatches\": {\n" +
						"      \"album\": [\n" +
						"        {\n" +
						"          \"name\": \"Deftones\",\n" +
						"          \"artist\": \"Deftones\",\n" +
						"          \"url\": \"https://www.last.fm/music/Deftones/Deftones\",\n" +
						"          \"image\": [\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/34s/ca155ef00da542a4aaa60ede1c2cc70f.png\",\n" +
						"              \"size\": \"small\"\n" +
						"            },\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/64s/ca155ef00da542a4aaa60ede1c2cc70f.png\",\n" +
						"              \"size\": \"medium\"\n" +
						"            },\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/174s/ca155ef00da542a4aaa60ede1c2cc70f.png\",\n" +
						"              \"size\": \"large\"\n" +
						"            },\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/300x300/ca155ef00da542a4aaa60ede1c2cc70f.png\",\n" +
						"              \"size\": \"extralarge\"\n" +
						"            }\n" +
						"          ],\n" +
						"          \"streamable\": \"0\",\n" +
						"          \"mbid\": \"ab6d3199-90d7-34de-8008-c6f072a5d43a\"\n" +
						"        },\n" +
						"        {\n" +
						"          \"name\": \"White Pony\",\n" +
						"          \"artist\": \"Deftones\",\n" +
						"          \"url\": \"https://www.last.fm/music/Deftones/White+Pony\",\n" +
						"          \"image\": [\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/34s/c1ee574cf6d84f7eb4bd35476c4e6ee6.png\",\n" +
						"              \"size\": \"small\"\n" +
						"            },\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/64s/c1ee574cf6d84f7eb4bd35476c4e6ee6.png\",\n" +
						"              \"size\": \"medium\"\n" +
						"            },\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/174s/c1ee574cf6d84f7eb4bd35476c4e6ee6.png\",\n" +
						"              \"size\": \"large\"\n" +
						"            },\n" +
						"            {\n" +
						"              \"#text\": \"https://lastfm-img2.akamaized.net/i/u/300x300/c1ee574cf6d84f7eb4bd35476c4e6ee6.png\",\n" +
						"              \"size\": \"extralarge\"\n" +
						"            }\n" +
						"          ],\n" +
						"          \"streamable\": \"0\",\n" +
						"          \"mbid\": \"c7e82aec-f36b-45ef-9eb6-0721825b210b\"\n" +
						"        }\n" +
						"      ]\n" +
						"    },\n" +
						"    \"@attr\": {\n" +
						"      \"for\": \"deftones\"\n" +
						"    }\n" +
						"  }\n" +
						"}";
		
		@Override
		protected Optional<JsonNode> doGet(Map<String, String> params) {
			JsonNode jsonNode;
			ObjectMapper mapper = new ObjectMapper();
			Optional<JsonNode> optional = Optional.empty();
			
			try {
				jsonNode = mapper.readTree(lastFMSearchResponse);
				optional = Optional.ofNullable(jsonNode);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return optional;
		}
	};
}