package co.uk.bittwisted.repository.mappers;

import co.uk.bittwisted.domain.MovieInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kvfbowden on 5/26/2017.
 */
public class MovieInfoDeserializer extends StdDeserializer<MovieInfo> {
	private Logger logger = LoggerFactory.getLogger(MovieInfoDeserializer.class);
	private SimpleDateFormat format;
	
	public MovieInfoDeserializer() {
		this(null);
		
		format = new SimpleDateFormat("yyyy-mm-dd");
	}
	
	public MovieInfoDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public MovieInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws
					IOException, JsonProcessingException {
		
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		String title = node.get("title").asText();
		String strDate = node.get("release_date").asText();
		String description = node.get("overview").asText();
		
		String year = "";
		try {
			Date date = format.parse(strDate);
			DateTime dateTime = new DateTime(date);
			year = String.valueOf(dateTime.getYear());
		} catch (ParseException e) {
			logger.error(String.format("The date [%s] could not be parsed by the date formatter.", strDate));
		}
		
		return new MovieInfo(title, year, description);
	}
}
