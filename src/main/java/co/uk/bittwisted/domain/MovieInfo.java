package co.uk.bittwisted.domain;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class MovieInfo extends BaseMediaInfo {
	private String description;
	
	public MovieInfo(String title, String year, String description) {
		super(title, year);
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MovieInfo)) return false;
		if (!super.equals(o)) return false;
		
		MovieInfo movieInfo = (MovieInfo) o;
		
		return description.equals(movieInfo.description);
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + description.hashCode();
		return result;
	}
}
