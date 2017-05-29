package co.uk.bittwisted.domain;

/**
 * Created by kvfbowden on 5/25/2017.
 */
public class BaseMediaInfo {
	private String title;
	private String year;
	
	public BaseMediaInfo(String title) {
		this.title = title;
	}
	
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BaseMediaInfo)) return false;
		
		BaseMediaInfo that = (BaseMediaInfo) o;
		
		return title.equals(that.title) && (year != null ? year.equals(that.year) : that.year == null);
	}
	
	@Override
	public int hashCode() {
		int result = title.hashCode();
		result = 31 * result + (year != null ? year.hashCode() : 0);
		return result;
	}
}
