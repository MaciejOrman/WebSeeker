package pl.wroc.pwr.service.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import pl.wroc.pwr.repository.dao.RankedLinkDAO;
import pl.wroc.pwr.repository.model.RankedLink;
import pl.wroc.pwr.service.rest.data.LinkAndGrade;
import pl.wroc.pwr.service.rest.data.NickAndQuery;
import pl.wroc.pwr.service.rest.data.ReccomendedLinks;
import pl.wroc.pwr.service.rest.data.comparator.MeanGradeComparator;


@RestController
public class MeanRateSearchService {

	@Autowired
	RankedLinkDAO rankedLinksDAO;

	@RequestMapping(value = "/meanratesearch", method = RequestMethod.POST)
	public @ResponseBody ReccomendedLinks collSearch(
			@RequestBody NickAndQuery nickAndQuery) {
		System.out.println(nickAndQuery);
		if (nickAndQuery != null && nickAndQuery.getQuery() != null) {
			List<RankedLink> rankedLinks = rankedLinksDAO
					.findByQuery(nickAndQuery.getQuery());
			double meanGrade;

			ReccomendedLinks reccomendedLinks = new ReccomendedLinks();
			reccomendedLinks.setNick(nickAndQuery.getNick());
			LinkAndGrade linkAndGrade;
			List<LinkAndGrade> linksAndGrades = new ArrayList<LinkAndGrade>();

			for (RankedLink rankedLink : rankedLinks) {
				meanGrade = coutMeanGradeForRankedLink(rankedLinks,
						rankedLink.getUrl());
				linkAndGrade = new LinkAndGrade(rankedLink.getTitle(),
						rankedLink.getUrl(), rankedLink.getKwic());
				linkAndGrade.setMeanGrade(meanGrade);
				if (!urlExists(linksAndGrades, linkAndGrade.getUrl())) {
					linksAndGrades.add(linkAndGrade);
				}
			}
			Collections.sort(linksAndGrades, new MeanGradeComparator());
			reccomendedLinks.setLinksAndGrades(linksAndGrades);
			System.out.println(reccomendedLinks);

			return reccomendedLinks;

		}
		return null;
	}

	private boolean urlExists(List<LinkAndGrade> list, String url) {
		for (LinkAndGrade element : list) {
			if (element.getUrl().equals(url)) {
				return true;
			}
		}
		return false;
	}

	private double coutMeanGradeForRankedLink(List<RankedLink> rankedLinks,
			String url) {
		Iterator<RankedLink> iterator = rankedLinks.iterator();
		int gradesSum = 0;
		int counter = 0;
		while (iterator.hasNext()) {
			RankedLink rankedLink = iterator.next();
			if (rankedLink.getUrl().equals(url) && rankedLink.getGrade() != -1) {
				gradesSum += rankedLink.getGrade();
				counter++;
			}
		}
		return gradesSum / (double) counter;
	}

}
