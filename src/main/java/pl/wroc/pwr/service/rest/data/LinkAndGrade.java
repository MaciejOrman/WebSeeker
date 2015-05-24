package pl.wroc.pwr.service.rest.data;

public class LinkAndGrade {

	private String title;
	private String url;
	private String kwic;
	private int grade;
	private double meanGrade;
	public LinkAndGrade() {}
	
	public LinkAndGrade(String title, String url, String kwic) {
		this(title,url,kwic,-1);
	}
	
	public LinkAndGrade(String title, String url, String kwic, int grade) {
		super();
		this.title = title;
		this.url = url;
		this.kwic = kwic;
		this.grade = grade;
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

	@Override
	public String toString() {
		return "url=" + url+" grade: "+grade;
	}

	public double getMeanGrade() {
		return meanGrade;
	}

	public void setMeanGrade(double meanGrade) {
		this.meanGrade = meanGrade;
	}
	
	

	
	
	
	
	
}
