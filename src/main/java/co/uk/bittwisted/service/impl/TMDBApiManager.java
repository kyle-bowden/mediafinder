package co.uk.bittwisted.service.impl;

import co.uk.bittwisted.domain.MovieInfo;
import co.uk.bittwisted.repository.impl.TMDBApiRepository;
import co.uk.bittwisted.service.ApiManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by kvfbowden on 5/29/2017.
 */
public class TMDBApiManager implements ApiManager<MovieInfo> {
	private static String ARG_NAME = "tmdb";
	
	@Autowired
	private TMDBApiRepository repository;
	
	public TMDBApiManager() {}
	
	@Override
	public List<MovieInfo> search(String query) {
		return repository.search(query);
	}
	
	public String getArgName() {
		return ARG_NAME;
	}
}
