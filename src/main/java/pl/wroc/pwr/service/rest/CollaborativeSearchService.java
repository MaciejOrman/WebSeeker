package pl.wroc.pwr.service.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;







import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.repository.dao.RankedLinkDAO;
import pl.wroc.pwr.repository.model.RankedLink;
import pl.wroc.pwr.service.rest.data.LinkAndGrade;
import pl.wroc.pwr.service.rest.data.NickAndQuery;
import pl.wroc.pwr.service.rest.data.ReccomendedLinks;
import pl.wroc.pwr.service.rest.data.comparator.MeanGradeComparator;


@Controller
public class CollaborativeSearchService {
	
	private static final int MIN_SUP_PERCENTAGE = 49;
	
	private static final int MIN_RELI_PERCENTAGE = 75;
	
	private static final int MIN_RATE_FOR_USEFUL_LINK = 3;
	

	@Autowired
	RankedLinkDAO rankedLinksDAO;
	
	private List<RankedLink> rankedLinks;

	
	public @ResponseBody ReccomendedLinks collSearch(
			@RequestBody NickAndQuery nickAndQuery) {

		return null;
	}
	
	@RequestMapping(value = "/collsearch", method = RequestMethod.POST)
	public Map<String, Double> findFrequentItemsets(@RequestBody NickAndQuery nickAndQuery){
		System.out.println(nickAndQuery);
		HashSet<String> urlSet = new HashSet<String>();
		rankedLinks = rankedLinksDAO.findAll();
		
		for(RankedLink rankedLink: rankedLinks){
			urlSet.add(rankedLink.getUrl());			
		}
		int timesRated;
		double percentageSupport;
		
		Map<String, Double> frequentUrlAndSupportMap = new HashMap<String, Double>();
		for(String url: urlSet){
			timesRated = countTimesRated(url);
			percentageSupport = (timesRated/(double)countNumberOfDifferentUsers()*100);
			if(percentageSupport> MIN_SUP_PERCENTAGE){
				frequentUrlAndSupportMap.put(url, percentageSupport);
			}	
		}
		System.out.println(frequentUrlAndSupportMap);
		return frequentUrlAndSupportMap;

	}
	
	private int countTimesRated(String url){
		int counter=0;
		
		
		for(RankedLink rankedLink: rankedLinks){
			if(rankedLink.getUrl().equals(url)){
				counter++;
			}
		}
		return counter;
	}
	
	private int countNumberOfDifferentUsers(){
		
		HashSet<String> usernamesSet = new HashSet<String>();

		for(RankedLink rankedLink: rankedLinks){
			usernamesSet.add(rankedLink.getUsername());			
		}
		
		return usernamesSet.size();
	}
	
	





}
