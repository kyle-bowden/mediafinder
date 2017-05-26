package co.uk.bittwisted.domain;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class MovieInfo extends BaseMediaInfo {
	private String director;
	
	public MovieInfo(String title, String year, String director) {
		super(title, year);
		this.director = director;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
}
