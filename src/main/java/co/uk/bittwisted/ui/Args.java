package co.uk.bittwisted.ui;

import com.beust.jcommander.DynamicParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class Args {
	@DynamicParameter(names = "-D", description = "Two supported API's [tmdb(Movies), lastfm(Music)] - Example usages {-Dapi=tmdb -Dquery=\"District 9\"} {-Dapi=lastfm -Dquery=\"Deftones\"}")
	private Map<String, String> params = new HashMap<>();
	
	public Optional<String> getApi() {
		return Optional.ofNullable(params.getOrDefault("api", null));
	}
	
	public Optional<String> getSearchQuery() {
		return Optional.ofNullable(params.getOrDefault("query", null));
	}
}
