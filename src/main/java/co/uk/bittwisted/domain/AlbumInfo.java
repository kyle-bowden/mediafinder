package co.uk.bittwisted.domain;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class AlbumInfo extends BaseMediaInfo {
	private String artist;
	private String urlArtistInfo;
	
	public AlbumInfo(String title, String artist, String urlArtistInfo) {
		super(title);
		this.artist = artist;
		this.urlArtistInfo = urlArtistInfo;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getUrlArtistInfo() {
		return urlArtistInfo;
	}
	
	public void setUrlArtistInfo(String urlArtistInfo) {
		this.urlArtistInfo = urlArtistInfo;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AlbumInfo)) return false;
		
		AlbumInfo albumInfo = (AlbumInfo) o;
		
		return artist.equals(albumInfo.artist) && urlArtistInfo.equals(albumInfo.urlArtistInfo);
	}
	
	@Override
	public int hashCode() {
		int result = artist.hashCode();
		result = 31 * result + urlArtistInfo.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return "AlbumInfo{" +
						"title='" + getTitle() + '\'' +
						", artist='" + artist + '\'' +
						", urlArtistInfo='" + urlArtistInfo + '\'' +
						'}';
	}
}
