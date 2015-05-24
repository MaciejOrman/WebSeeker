package pl.wroc.pwr.service.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.wroc.pwr.faroo.dto.FarooResults;
import pl.wroc.pwr.faroo.dto.Link;
import pl.wroc.pwr.repository.dao.RankedLinkDAO;
import pl.wroc.pwr.repository.model.RankedLink;
import pl.wroc.pwr.service.rest.data.LinkAndGrade;
import pl.wroc.pwr.service.rest.data.NickAndQuery;
import pl.wroc.pwr.service.rest.data.ReccomendedLinks;
import pl.wroc.pwr.service.rest.data.comparator.MeanGradeComparator;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
public class SearchService {
	
	@Autowired
	RankedLinkDAO rankedLinksDAO;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody ReccomendedLinks search(@RequestBody NickAndQuery nickAndQuery)
			throws UnirestException, InterruptedException {
		System.out.println(nickAndQuery);
		HttpResponse<String> response = null;
		if (nickAndQuery != null && nickAndQuery.getQuery() != null) {
			response = searchInFaroo(nickAndQuery);
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				FarooResults farooLinks = mapper.readValue(response.getBody(), FarooResults.class);
				ReccomendedLinks reccomendedLinks = new ReccomendedLinks();
				reccomendedLinks.setNick(nickAndQuery.getNick());
				LinkAndGrade linkAndGrade;
				List<LinkAndGrade> linksAndGrades = new ArrayList<LinkAndGrade>();

				for(Link farooLink:farooLinks.getResults() ) {
					linkAndGrade = new LinkAndGrade(farooLink.getTitle(),farooLink.getUrl() , farooLink.getKwic());
					linksAndGrades.add(linkAndGrade);
				}
				reccomendedLinks.setLinksAndGrades(linksAndGrades);
				System.out.println(reccomendedLinks);
				
				return reccomendedLinks;

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		/*
		 * JsonNode body = response.getBody();
		 * 
		 * org.json.JSONArray jsonArray
		 * =body.getObject().getJSONArray("RelatedTopics");
		 */

		// ReccomendedLinks links;

		// ObjectMapper mapper = new ObjectMapper();

		/*
		 * try{ links = mapper.convertValue(response.getBody()., links); }
		 */

		return null;
	}
	
	@RequestMapping(value = "/collsearch", method = RequestMethod.POST)
	public @ResponseBody ReccomendedLinks collSearch(@RequestBody NickAndQuery nickAndQuery){
		System.out.println(nickAndQuery);
		if (nickAndQuery != null && nickAndQuery.getQuery() != null) {
			List<RankedLink> rankedLinks = rankedLinksDAO.findByQuery(nickAndQuery.getQuery());
			
			//List<LinkAndGrade> reccomendedLinks = new ArrayList<LinkAndGrade>();	
			//LinkAndGrade reccomendedLink;
			double meanGrade;
			
			
				ReccomendedLinks reccomendedLinks = new ReccomendedLinks();
				reccomendedLinks.setNick(nickAndQuery.getNick());
				LinkAndGrade linkAndGrade;
				List<LinkAndGrade> linksAndGrades = new ArrayList<LinkAndGrade>();
			
				for(RankedLink rankedLink:rankedLinks) {			
					meanGrade= coutMeanGradeForRankedLink(rankedLinks, rankedLink.getUrl());
					linkAndGrade = new LinkAndGrade(rankedLink.getTitle(),rankedLink.getUrl() , rankedLink.getKwic());
					linkAndGrade.setMeanGrade(meanGrade);
					if(!urlExists(linksAndGrades, linkAndGrade.getUrl()))   {
						linksAndGrades.add(linkAndGrade);
					}
				}
				Collections.sort(linksAndGrades,new MeanGradeComparator());
				reccomendedLinks.setLinksAndGrades(linksAndGrades);
				System.out.println(reccomendedLinks);
				
				return reccomendedLinks;

		
			
		}
		return null;
	}
	
	private boolean urlExists(List<LinkAndGrade> list, String url) {
		for(LinkAndGrade element: list) {
			if(element.getUrl().equals(url)) {
				return true;
			}
		}
		return false;
	}

	private double coutMeanGradeForRankedLink(List<RankedLink> rankedLinks, String url) {
		Iterator<RankedLink> iterator = rankedLinks.iterator();
		int gradesSum = 0;
		int counter =0;
		while(iterator.hasNext()) {
			RankedLink rankedLink =iterator.next();
			if(rankedLink.getUrl().equals(url) && rankedLink.getGrade() !=-1) {
				gradesSum+=rankedLink.getGrade();
				counter++;
			}
		}
		return gradesSum/(double)counter;
	}
	
	

	private HttpResponse<String> searchInFaroo(NickAndQuery nickAndQuery)
			throws UnirestException {
		HttpResponse<String> response;
		String queryModified = nickAndQuery.getQuery().replace(" ", "+");

		response = Unirest
				.get("https://faroo-faroo-web-search.p.mashape.com/api?q="
						+ queryModified)
				.header("X-Mashape-Key",
						"EQ0BgAHsmxmsh6FylZTqSqqkTF7op1ov4BzjsntBXusNcAgWbU")
				.header("Accept", "application/json").asString();
		return response;
	}

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
