package se.depi.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import se.depi.model.Issue;
import se.depi.model.Status;
import se.depi.model.Team;
import se.depi.model.User;
import se.depi.model.WorkItem;
import se.depi.repository.IssueRepository;
import se.depi.repository.TeamRepository;
import se.depi.repository.UserRepository;
import se.depi.repository.WorkItemRepository;
import se.depi.service.ServiceTransaction.Action;

@Component
public class EntityService {

	private final UserRepository userRepository;
	private final TeamRepository teamRepository;
	private final WorkItemRepository workItemRepository;
	private final IssueRepository issueRepository;

	@Autowired
	public EntityService(UserRepository userRepository, TeamRepository teamRepository,
			WorkItemRepository workItemRepository, IssueRepository issueRepository) {
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
		this.workItemRepository = workItemRepository;
		this.issueRepository = issueRepository;
	}

	public User addUser(User user) {
		if (user.getUsername().length() < 5) {
			throw new ServiceException("Username too short.");
		}
		return userRepository.save(user);
	}

	public User findUser(Long userId) {
		return userRepository.findOne(userId);
	}

	public Collection<User> findByFirstname(String firstname) {
		return userRepository.findByFirstname(firstname);
	}

	public Collection<User> findUsersInTeam(Long teamId) {
		return userRepository.findByTeamId(teamId);
	}

	public User inactivateUser(Long userId) {
		return execute(() -> {
			User user = findUser(userId);
			user.setStatus(Status.UNACTIVE);
			setItemStatus(user.getId());
			return userRepository.save(user);
		});
	}

	public User addUserToTeam(Long id, Long teamId) {
		return execute(() -> {
			if (!teamSizeLessThenTen(teamId)) {
				throw new ServiceException("Team size too big.");
			}
			User user = findUser(id);
			Team team = findTeam(teamId);
			user.setTeam(team);
			return addUser(user);
		});
	}

	public Team createTeam(Team team) {
		return teamRepository.save(team);
	}

	public Team findTeam(Long teamId) {
		return teamRepository.findOne(teamId);
	}

	public Collection<Team> findAllTeams() {
		return teamRepository.getAllTeams();
	}

	public WorkItem createItem(WorkItem workitem) {
		return workItemRepository.save(workitem);
	}

	public void removeWorkItem(Long id) {
		workItemRepository.delete(id);
	}

	public WorkItem assignWorkItem(Long itemId, Long userId) {
		if (!isActiveUser(userId)) {
			throw new ServiceException("User is not active.");
		}
		if (!rightAmountOfWorkItems(userId)) {
			throw new ServiceException("User already have 5 workitems.");
		}
		User user = findUser(userId);
		WorkItem item = findWorkItem(itemId);
		item.setUser(user);
		return createItem(item);
	}

	public WorkItem findWorkItem(Long itemId) {
		return workItemRepository.findOne(itemId);
	}

	public Collection<WorkItem> findByStatus(Status status) {
		return workItemRepository.findByStatus(status);
	}

	public Collection<WorkItem> findItemsInTeam(Long teamId) {
		return workItemRepository.findByUserTeamId(teamId);
	}

	public Collection<WorkItem> findItemsByUser(Long userId) {
		return workItemRepository.findByUserId(userId);
	}

	public Collection<WorkItem> findItemsByDescription(String description) {
		return workItemRepository.findByDescriptionContains(description);
	}

	public Issue saveAndUpdateIssue(Issue issue) {
		return issueRepository.save(issue);
	}

	public void assignIssue(Long issueId, Long itemId) {
		if (!itemStatusDone(itemId)) {
			throw new ServiceException("Status is not done.");
		}
		Issue issue = getIssueById(issueId);
		WorkItem item = findWorkItem(itemId);
		item.setIssue(issue);
		setItemStatus(item);
		createItem(item);
	}

	public Issue getIssueById(Long id) {
		return issueRepository.findOne(id);
	}

	public Collection<WorkItem> findIssuedItems() {
		return issueRepository.getIssuedWorkItems();
	}

	private <T> T execute(Action<T> action) throws DataAccessException {
		try {
			return action.execute();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setItemStatus(Long userId) {
		Collection<WorkItem> items = findItemsByUser(userId);
		items.forEach(workitem -> workitem.setStatus(Status.UNSTARTED));

	}

	private boolean teamSizeLessThenTen(Long teamId) {
		return findUsersInTeam(teamId).size() < 10;
	}

	private boolean isActiveUser(Long userId) {
		return findUser(userId).getStatus() == Status.ACTIVE;
	}

	private boolean rightAmountOfWorkItems(Long userId) {
		return findItemsByUser(userId).size() < 5;
	}

	private void setItemStatus(WorkItem workItem) {
		workItem.setStatus(Status.UNSTARTED);
	}

	private boolean itemStatusDone(Long itemId) {
		return findWorkItem(itemId).getStatus() == Status.DONE;
	}

}
