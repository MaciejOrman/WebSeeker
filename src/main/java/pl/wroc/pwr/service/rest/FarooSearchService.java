package pl.wroc.pwr.service.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.faroo.dto.FarooResults;
import pl.wroc.pwr.faroo.dto.Link;
import pl.wroc.pwr.service.rest.data.LinkAndGrade;
import pl.wroc.pwr.service.rest.data.NickAndQuery;
import pl.wroc.pwr.service.rest.data.ReccomendedLinks;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class FarooSearchService {

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

		return null;
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
	
}
