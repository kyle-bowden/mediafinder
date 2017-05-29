package co.uk.bittwisted.service.impl;

import co.uk.bittwisted.domain.AlbumInfo;
import co.uk.bittwisted.repository.impl.LastFMApiRepository;
import co.uk.bittwisted.service.ApiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kvfbowden on 5/29/2017.
 */
@Service
public class LastFMApiManager implements ApiManager<AlbumInfo> {
	private static String ARG_NAME = "lastfm";
	
	@Autowired
	private LastFMApiRepository repository;
	
	public LastFMApiManager() {}
	
	@Override
	public List<AlbumInfo> search(String query) {
		return repository.search(query);
	}
	
	public String getArgName() {
		return ARG_NAME;
	}
}
