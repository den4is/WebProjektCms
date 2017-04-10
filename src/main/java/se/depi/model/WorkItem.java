package se.depi.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class WorkItem extends AbstractEntity {

	@Column(unique = true, nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String description;
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	private User user;

	@OneToOne(cascade = CascadeType.REMOVE)
	private Issue issue;
	
	protected WorkItem() {
	}

	public WorkItem(String name, String description, Status status) {
		this.name = name;
		this.description = description;
		this.status = status;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	public Status getStatus() {
		return status;
	}

}
