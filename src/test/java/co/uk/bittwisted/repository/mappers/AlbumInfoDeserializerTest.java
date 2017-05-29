package co.uk.bittwisted.repository.mappers;

import co.uk.bittwisted.domain.AlbumInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kvfbowden on 5/26/2017.
 */
public class AlbumInfoDeserializerTest {
	
	private final String responseJSON =
					" {\n" +
					"   \"name\": \"White Pony\",\n" +
					"   \"artist\": \"Deftones\",\n" +
					"   \"url\": \"https://www.last.fm/music/Deftones/Deftones\"\n" +
					" }";
	
	@Test
	public void testCustomDeserializationOfJSONToAlbumInfo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(AlbumInfo.class, new AlbumInfoDeserializer());
		mapper.registerModule(module);
		
		AlbumInfo readValue = mapper.readValue(responseJSON, AlbumInfo.class);
		
		assertNotNull(readValue);
		assertEquals(readValue.getTitle(), "White Pony");
		assertEquals(readValue.getArtist(), "Deftones");
		assertEquals(readValue.getUrlArtistInfo(), "https://www.last.fm/music/Deftones/Deftones");
	}
}
