package co.uk.bittwisted.repository.mappers;

import co.uk.bittwisted.domain.AlbumInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Created by kvfbowden on 5/26/2017.
 */
public class AlbumInfoDeserializer extends StdDeserializer<AlbumInfo> {
	
	public AlbumInfoDeserializer() {
		this(null);
	}
	
	public AlbumInfoDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public AlbumInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws
					IOException, JsonProcessingException {
		
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		String artist = node.get("artist").asText();
		String title = node.get("name").asText();
		String urlArtistInfo = node.get("url").asText();
		
		return new AlbumInfo(title, artist, urlArtistInfo);
	}
}
