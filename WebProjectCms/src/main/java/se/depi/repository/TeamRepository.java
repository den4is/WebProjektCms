package se.depi.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.depi.model.Team;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long>{

	@Query(value = "SELECT t from Team t")
	Collection<Team> getAllTeams();
	
}
