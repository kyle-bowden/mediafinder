package co.uk.bittwisted.domain;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class AlbumInfo extends BaseMediaInfo {
	private String artist;
	private String producer;
	
	public AlbumInfo(String title, String year, String artist, String producer) {
		super(title, year);
		this.artist = artist;
		this.producer = producer;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getProducer() {
		return producer;
	}
	
	public void setProducer(String producer) {
		this.producer = producer;
	}
}
