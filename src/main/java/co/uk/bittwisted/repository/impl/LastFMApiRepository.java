package co.uk.bittwisted.repository.impl;

import co.uk.bittwisted.domain.AlbumInfo;
import co.uk.bittwisted.repository.mappers.AlbumInfoDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kvfbowden on 5/25/2017.
 */
@Service
public class LastFMApiRepository extends AbstractApiRepository<AlbumInfo> {
	private static final String API_KEY       = "f46a88037b946a829463c54c8e81b71b";
	private static final String API_BASE_URL  = "http://ws.audioscrobbler.com/2.0/";
	
	private String resultsNodeName = "album";
	private String queryParamName = "album";
	
	private Map<String, String> searchParams;
	private final String PARAM_LIMIT = "limit";
	private final String PARAM_METHOD = "method";
	private final String PARAM_FORMAT = "format";
	private final String PARAM_API_KEY = "api_key";
	private final String VALUE_METHOD = "album.search";
	private final String VALUE_FORMAT = "json";
	private final int VALUE_LIMIT_QUERY_RESULTS = 10;
	
	public LastFMApiRepository() {
		super(API_BASE_URL);
		
		searchParams = new HashMap<>();
		searchParams.put(PARAM_METHOD, VALUE_METHOD);
		searchParams.put(PARAM_API_KEY, API_KEY);
		searchParams.put(PARAM_LIMIT, String.valueOf(VALUE_LIMIT_QUERY_RESULTS));
		searchParams.put(PARAM_FORMAT, VALUE_FORMAT);
	}
	
	@Override
	public List<AlbumInfo> search(String query) {
		searchParams.put(queryParamName, query);
		
		List<AlbumInfo> albumInfoList = new ArrayList<>();
		Optional<JsonNode> optional = doGet(searchParams);
		
		if(optional.isPresent()) {
			JsonNode jsonNode = optional.get();
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(AlbumInfo.class, new AlbumInfoDeserializer());
			mapper.registerModule(module);
			
			JsonNode albumNode = jsonNode.findValue(resultsNodeName);
			if(albumNode != null) {
				albumNode.forEach(node -> {
					AlbumInfo albumInfo = mapper.convertValue(node, AlbumInfo.class);
					albumInfoList.add(albumInfo);
				});
			}
		}
		
		return albumInfoList;
	}
}
