package co.uk.bittwisted.domain;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class BaseMediaInfo {
	private String title;
	private String year;
	
	public BaseMediaInfo(String title, String year) {
		this.title = title;
		this.year = year;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
}
