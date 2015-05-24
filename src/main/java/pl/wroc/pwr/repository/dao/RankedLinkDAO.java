package pl.wroc.pwr.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.wroc.pwr.repository.model.RankedLink;

public interface RankedLinkDAO extends JpaRepository<RankedLink, Long>{

	public List<RankedLink> findByQuery(String query);
}
