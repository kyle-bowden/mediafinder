package co.uk.bittwisted.repository.mappers;

import co.uk.bittwisted.domain.MovieInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kvfbowden on 5/29/2017.
 */
public class MovieInfoDeserializerTest {
	
	private final String responseJSON = "{\n" +
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
					"    }";
	
	@Test
	public void testCustomDeserializationOfJSONToMovieInfo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(MovieInfo.class, new MovieInfoDeserializer());
		mapper.registerModule(module);
		
		MovieInfo readValue = mapper.readValue(responseJSON, MovieInfo.class);
		
		assertNotNull(readValue);
		assertEquals(readValue.getTitle(), "Star Wars");
		assertEquals(readValue.getYear(), "1977");
		assertEquals(readValue.getDescription(), "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.");
	}
}