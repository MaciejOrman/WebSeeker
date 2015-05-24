package pl.wroc.pwr.faroo.dto;

import java.util.Date;

public class Link {

	private String title;
	private String kwic;
	private String content;
	private String url;
	private String iurl;
	private String domain;
	private String author;
	private boolean news;
	private int votes;
	private Date date;
	private String[] related;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKwic() {
		return kwic;
	}
	public void setKwic(String kwic) {
		this.kwic = kwic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIurl() {
		return iurl;
	}
	public void setIurl(String iurl) {
		this.iurl = iurl;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isNews() {
		return news;
	}
	public void setNews(boolean news) {
		this.news = news;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String[] getRelated() {
		return related;
	}
	public void setRelated(String[] related) {
		this.related = related;
	}
	@Override
	public String toString() {
		return "url=" + url + " ";
	}
	
	
	
	
}
