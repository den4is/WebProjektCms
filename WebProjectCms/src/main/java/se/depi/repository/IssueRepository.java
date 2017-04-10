package se.depi.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.depi.model.Issue;
import se.depi.model.WorkItem;

public interface IssueRepository extends PagingAndSortingRepository<Issue, Long> {
	
	
	@Query("SELECT w FROM WorkItem w join fetch w.issue i WHERE i.id IS NOT NULL")
	Collection<WorkItem> getIssuedWorkItems();
	
	
}
