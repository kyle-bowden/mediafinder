package co.uk.bittwisted.repository;

import co.uk.bittwisted.domain.MovieInfo;
import co.uk.bittwisted.repository.impl.TMDBApiRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kvfbowden on 5/29/2017.
 */
public class TMDBApiRepositoryTest {
	@Test
	public void searchTMDBRepository() throws Exception {
		List<MovieInfo> movieInfoList = mockRepository.search("");
		
		assertNotNull(movieInfoList);
		assertEquals(movieInfoList.size(), 3);
		
		List<MovieInfo> expectedMovieInfoList = new ArrayList<>();
		expectedMovieInfoList.add(new MovieInfo("Star Wars", "1977", "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire."));
		expectedMovieInfoList.add(new MovieInfo("Star Wars: The Force Awakens", "2015", "Thirty years after defeating the Galactic Empire, Han Solo and his allies face a new threat from the evil Kylo Ren and his army of Stormtroopers."));
		expectedMovieInfoList.add(new MovieInfo("Rogue One: A Star Wars Story", "2016", "A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy."));
		
		assertEquals(expectedMovieInfoList, movieInfoList);
	}
	
	private TMDBApiRepository mockRepository = new TMDBApiRepository() {
		private final String tmdbSearchResponse = "{\n" +
						"  \"page\": 1,\n" +
						"  \"results\": [\n" +
						"    {\n" +
						"      \"poster_path\": \"/tvSlBzAdRE29bZe5yYWrJ2ds137.jpg\",\n" +
						"      \"adult\": false,\n" +
						"      \"overview\": \"Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.\",\n" +
						"      \"release_date\": \"1977-05-25\",\n" +
						"      \"genre_ids\": [\n" +
						"        12,\n" +
						"        28,\n" +
						"        878\n" +
						"      ],\n" +
						"      \"id\": 11,\n" +
						"      \"original_title\": \"Star Wars\",\n" +
						"      \"original_language\": \"en\",\n" +
						"      \"title\": \"Star Wars\",\n" +
						"      \"backdrop_path\": \"/4iJfYYoQzZcONB9hNzg0J0wWyPH.jpg\",\n" +
						"      \"popularity\": 9.522642,\n" +
						"      \"vote_count\": 5679,\n" +
						"      \"video\": false,\n" +
						"      \"vote_average\": 8\n" +
						"    },\n" +
						"    {\n" +
						"      \"poster_path\": \"/weUSwMdQIa3NaXVzwUoIIcAi85d.jpg\",\n" +
						"      \"adult\": false,\n" +
						"      \"overview\": \"Thirty years after defeating the Galactic Empire, Han Solo and his allies face a new threat from the evil Kylo Ren and his army of Stormtroopers.\",\n" +
						"      \"release_date\": \"2015-12-15\",\n" +
						"      \"genre_ids\": [\n" +
						"        28,\n" +
						"        12,\n" +
						"        878,\n" +
						"        14\n" +
						"      ],\n" +
						"      \"id\": 140607,\n" +
						"      \"original_title\": \"Star Wars: The Force Awakens\",\n" +
						"      \"original_language\": \"en\",\n" +
						"      \"title\": \"Star Wars: The Force Awakens\",\n" +
						"      \"backdrop_path\": \"/c2Ax8Rox5g6CneChwy1gmu4UbSb.jpg\",\n" +
						"      \"popularity\": 11.99115,\n" +
						"      \"vote_count\": 6730,\n" +
						"      \"video\": false,\n" +
						"      \"vote_average\": 7.5\n" +
						"    },\n" +
						"    {\n" +
						"      \"poster_path\": \"/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg\",\n" +
						"      \"adult\": false,\n" +
						"      \"overview\": \"A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.\",\n" +
						"      \"release_date\": \"2016-12-14\",\n" +
						"      \"genre_ids\": [\n" +
						"        878\n" +
						"      ],\n" +
						"      \"id\": 330459,\n" +
						"      \"original_title\": \"Rogue One: A Star Wars Story\",\n" +
						"      \"original_language\": \"en\",\n" +
						"      \"title\": \"Rogue One: A Star Wars Story\",\n" +
						"      \"backdrop_path\": \"/tZjVVIYXACV4IIIhXeIM59ytqwS.jpg\",\n" +
						"      \"popularity\": 15.76152,\n" +
						"      \"vote_count\": 3780,\n" +
						"      \"video\": false,\n" +
						"      \"vote_average\": 7.3\n" +
						"    }\n" +
						"  ],\n" +
						"  \"total_results\": 106,\n" +
						"  \"total_pages\": 6\n" +
						"}";
		
		@Override
		protected Optional<JsonNode> doGet(Map<String, String> params) {
			JsonNode jsonNode;
			ObjectMapper mapper = new ObjectMapper();
			Optional<JsonNode> optional = Optional.empty();
			
			try {
				jsonNode = mapper.readTree(tmdbSearchResponse);
				optional = Optional.ofNullable(jsonNode);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return optional;
		}
	};
}