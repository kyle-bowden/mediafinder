package co.uk.bittwisted.ui.config;

import co.uk.bittwisted.repository.impl.LastFMApiRepository;
import co.uk.bittwisted.repository.impl.TMDBApiRepository;
import co.uk.bittwisted.service.impl.LastFMApiManager;
import co.uk.bittwisted.service.impl.TMDBApiManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kvfbowden on 5/29/2017.
 */
@Configuration
public class AppConfig {
	
	@Bean(name = "lastFMApiRepository")
	public LastFMApiRepository lastFMApiRepository() {
		return new LastFMApiRepository();
	}
	
	@Bean(name = "lastFMApiManager")
	public LastFMApiManager lastFMApiManager() {
		return new LastFMApiManager();
	}
	
	@Bean(name = "tmdbApiRepository")
	public TMDBApiRepository tmdbApiRepository() {
		return new TMDBApiRepository();
	}
	
	@Bean(name = "tmdbApiManager")
	public TMDBApiManager tmdbApiManager() {
		return new TMDBApiManager();
	}
}
