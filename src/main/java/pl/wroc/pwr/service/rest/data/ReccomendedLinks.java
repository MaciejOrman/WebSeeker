package pl.wroc.pwr.service.rest.data;

import java.util.List;



public class ReccomendedLinks {
	private String nick;
	private String query;
	private List<LinkAndGrade> linksAndGrades;
	
	public ReccomendedLinks(){

	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
	public List<LinkAndGrade> getLinksAndGrades() {
		return linksAndGrades;
	}

	public void setLinksAndGrades(List<LinkAndGrade> linksAndGrades) {
		this.linksAndGrades = linksAndGrades;
	}

	@Override
	public String toString() {
		return "ReccomendedLinks [nick=" + nick + ", query=" + query
				+ ", linksAndGrades=" + linksAndGrades + "]";
	}

	


	
}
