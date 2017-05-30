package co.uk.bittwisted.repository.impl;

import co.uk.bittwisted.common.exceptions.MediaFinderException;
import co.uk.bittwisted.domain.MovieInfo;
import co.uk.bittwisted.repository.mappers.MovieInfoDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class TMDBApiRepository extends AbstractApiRepository<MovieInfo> {
	private Logger logger = LoggerFactory.getLogger(TMDBApiRepository.class);
	
	private static final String API_KEY       = "4552703f32df6373eedd611f8a6d3e6d";
	private static final String API_BASE_URL  = "https://api.themoviedb.org/3/search/movie/";
	
	private String queryParamName = "query";
	private String resultsNodeName = "results";
	
	private Map<String, String> searchParams;
	private final String PARAM_API_KEY = "api_key";
	private final String PARAM_LANGUAGE = "language";
	private final String PARAM_PAGE_COUNT = "page";
	private final String PARAM_ADULT_CONTENT = "include_adult";
	private final int VALUE_LIMIT_PAGE_COUNT = 1;
	private final String VALUE_LANGUAGE = "en-US";
	private final boolean VALUE_ADULT_CONTENT = false;
	
	public TMDBApiRepository() {
		super(API_BASE_URL);
		
		searchParams = new HashMap<>();
		searchParams.put(PARAM_API_KEY, API_KEY);
		searchParams.put(PARAM_LANGUAGE, VALUE_LANGUAGE);
		searchParams.put(PARAM_PAGE_COUNT, String.valueOf(VALUE_LIMIT_PAGE_COUNT));
		searchParams.put(PARAM_ADULT_CONTENT, String.valueOf(VALUE_ADULT_CONTENT));
	}
	
	@Override
	public List<MovieInfo> search(String query) throws MediaFinderException {
		searchParams.put(queryParamName, query);
		
		List<MovieInfo> movieInfoList = new ArrayList<>();
		Optional<JsonNode> optional;
		try {
			optional = doGet(searchParams);
		} catch (IOException | IllegalStateException | URISyntaxException e) {
			logger.error(String.format(MSG_GENERIC_FAILED_TO_PROCESS_REQUEST, API_BASE_URL, e.getLocalizedMessage()));
			throw new MediaFinderException(String.format(MSG_GENERIC_FAILED_TO_PROCESS_REQUEST, API_BASE_URL, e.getLocalizedMessage()));
		}
		
		if(optional.isPresent()) {
			JsonNode jsonNode = optional.get();
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(MovieInfo.class, new MovieInfoDeserializer());
			mapper.registerModule(module);
			
			JsonNode movieNode = jsonNode.findValue(resultsNodeName);
			if(movieNode != null) {
				movieNode.forEach(node -> {
					MovieInfo movieInfo = mapper.convertValue(node, MovieInfo.class);
					movieInfoList.add(movieInfo);
				});
			}
		}
		
		return movieInfoList;
	}
}
