package pl.wroc.pwr.service.rest.data.comparator;

import java.util.Comparator;

import pl.wroc.pwr.service.rest.data.LinkAndGrade;

public class MeanGradeComparator implements Comparator<LinkAndGrade> {

	@Override
	public int compare(LinkAndGrade o1, LinkAndGrade o2) {
		if(o1.getMeanGrade()>o2.getMeanGrade()) return -1;
		else if(o1.getMeanGrade()<o2.getMeanGrade()) return 1;
		else return 0;
	}

}
