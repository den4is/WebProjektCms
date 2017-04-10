package se.depi.repository;

import java.util.Collection;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.depi.model.Status;
import se.depi.model.WorkItem;

public interface WorkItemRepository extends PagingAndSortingRepository<WorkItem, Long> {

	Collection<WorkItem> findByStatus(Status status);

	Collection<WorkItem> findByUserTeamId(Long id);

	Collection<WorkItem> findByUserId(Long id);

	Collection<WorkItem> findByDescriptionContains(String description);

}