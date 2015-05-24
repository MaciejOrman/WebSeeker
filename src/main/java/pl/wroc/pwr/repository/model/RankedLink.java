package pl.wroc.pwr.repository.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(RankedLinkId.class)
public class RankedLink implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String username;
	@Id
	private String url;
	private String query;
	private String title;
	private String kwic;
	private int grade;
	
	protected RankedLink() {
		
	}

	public RankedLink(String username, String title, String url, String kwic,
			int grade, String query) {
		super();
		this.username = username;
		this.title = title;
		this.url = url;
		this.kwic = kwic;
		this.grade = grade;
		this.query = query;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKwic() {
		return kwic;
	}

	public void setKwic(String kwic) {
		this.kwic = kwic;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "RankedLink [username=" + username + ", url=" + url + ", query="
				+ query + ", title=" + title + ", kwic=" + kwic + ", grade="
				+ grade + "]";
	}	
	
	
}
