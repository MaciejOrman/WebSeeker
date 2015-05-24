package pl.wroc.pwr.service.rest;

import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.wroc.pwr.repository.dao.RankedLinkDAO;
import pl.wroc.pwr.repository.model.RankedLink;
import pl.wroc.pwr.service.rest.data.LinkAndGrade;
import pl.wroc.pwr.service.rest.data.ReccomendedLinks;

@RestController
public class RatingService {

	@Autowired
	RankedLinkDAO rankedLinksDAO;
	
	@RequestMapping(value = "/grades", method = RequestMethod.POST )
	public void receiveGrades(@RequestBody ReccomendedLinks linksWithGrades) throws JsonMappingException{
		System.out.println(linksWithGrades);
		String username = linksWithGrades.getNick();
		RankedLink rankedLink;
		
		for(LinkAndGrade linkAndGrade: linksWithGrades.getLinksAndGrades()) {
			int grade =linkAndGrade.getGrade();
			if(grade!=-1) {
				rankedLink = new RankedLink(username, linkAndGrade.getTitle(), linkAndGrade.getUrl(), linkAndGrade.getKwic(),grade, linksWithGrades.getQuery());
				System.out.println(rankedLink);
				rankedLinksDAO.save(rankedLink);
			}
		}
	}
}
