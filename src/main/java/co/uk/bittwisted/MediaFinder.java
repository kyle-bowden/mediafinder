package co.uk.bittwisted;

import co.uk.bittwisted.common.exceptions.MediaFinderException;
import co.uk.bittwisted.domain.AlbumInfo;
import co.uk.bittwisted.domain.MovieInfo;
import co.uk.bittwisted.service.impl.LastFMApiManager;
import co.uk.bittwisted.service.impl.TMDBApiManager;
import co.uk.bittwisted.ui.Args;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class MediaFinder implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(MediaFinder.class);
    
    private final String APPLICATION_NAME       = "Media Finder";
    private final String TEXT_TITLE_MUSIC       = "Search API [%s] for Music Album [%s]";
    private final String TEXT_FOUND_MUSIC       = "Found Music Album [%s]";
    private final String TEXT_TITLE_MOVIE       = "Search API [%s] for Movie [%s]";
    private final String TEXT_FOUND_MOVIE       = "Found Movie [%s]";
    
    private final String MSG_INVALID_API        = "There are only two supported API's for this app, please use one of the following [tmdb(Movies), lastfm(Music Albums)]";
    private final String MSG_NO_API_COMMAND     = "You will need to pass the api command e.g -Dapi=lastfm";
    private final String MSG_NO_QUERY_COMMAND   = "You will need to pass a query string command e.g -Dquery=\"Star Wars\"";
    private final String MSG_NO_RESULTS         = "API [%s] returned no search results for query [%s].";
    private final String MSG_INTERNAL_ERROR     = "There was an internal error and the request could not be processed.";
    
    @Autowired
    private LastFMApiManager lastFMApiManager;
    
    @Autowired
    private TMDBApiManager tmdbApiManager;
    
    @Autowired
    private ConfigurableApplicationContext context;
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(MediaFinder.class)
                .logStartupInfo(false)
                .run(args);
    }
    
    @Override
    public void run(String... strings) throws Exception {
        Args args = new Args();
        JCommander jc = JCommander.newBuilder()
                .addObject(args)
                .build();
        jc.setProgramName(APPLICATION_NAME);
        jc.setCaseSensitiveOptions(false);
        jc.usage();
    
        try {
            jc.parse(strings);
        } catch (ParameterException pe) {
            logger.warn(pe.getLocalizedMessage());
        }
        
        Optional<String> optionalApi = args.getApi();
        if(optionalApi.isPresent()) {
            String api = optionalApi.get();
            String query = getQuery(args);
            if(!query.equals("")) {
                if(api.equals(lastFMApiManager.getArgName())) {
                    handleMusicSearch(query);
                } else
                if(api.equals(tmdbApiManager.getArgName())) {
                    handleMovieSearch(query);
                } else {
                    // not a valid api specified
                    logger.warn(MSG_INVALID_API);
                }
            } else {
                // query cannot be empty
                logger.warn(MSG_NO_QUERY_COMMAND);
            }
        } else {
            // not a valid command
            logger.warn(MSG_NO_API_COMMAND);
        }
    
        // releasing all resources
        context.close();
        System.exit(0);
    }
    
    private void handleMovieSearch(String query) {
        try {
            List<MovieInfo> movieInfoList = tmdbApiManager.search(query);
            logger.info(String.format(TEXT_TITLE_MOVIE, tmdbApiManager.getArgName(), query));
    
            if(movieInfoList.size() > 0) {
                movieInfoList.forEach(movieInfo -> logger.info(String.format(TEXT_FOUND_MOVIE, movieInfo.toString())));
            } else {
                logger.info(String.format(MSG_NO_RESULTS, tmdbApiManager.getArgName(), query));
            }
        } catch (MediaFinderException e) {
            logger.info(MSG_INTERNAL_ERROR);
        }
    }
    
    private void handleMusicSearch(String query) {
        try {
            List<AlbumInfo> albumInfoList = lastFMApiManager.search(query);
            logger.info(String.format(TEXT_TITLE_MUSIC, lastFMApiManager.getArgName(), query));
        
            if(albumInfoList.size() > 0) {
                albumInfoList.forEach(albumInfo -> logger.info(String.format(TEXT_FOUND_MUSIC, albumInfo.toString())));
            } else {
                logger.info(String.format(MSG_NO_RESULTS, lastFMApiManager.getArgName(), query));
            }
        } catch (MediaFinderException e) {
            logger.info(MSG_INTERNAL_ERROR);
        }
    }
    
    private String getQuery(Args args) {
        String query = "";
        Optional<String> defaultQuery = args.getSearchQuery();
        
        if(defaultQuery.isPresent()) {
            query = defaultQuery.get();
        }
        
        return query;
    }
}
