package se.depi.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	private String lastname;
	@Column(nullable = false, unique = true)
	private String username;
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	@JoinColumn(unique = true)
	private Team team;

	@OneToMany(mappedBy = "user")
	private Collection<WorkItem> workItems;
	
	protected User(){}

	public User(String firstname, String lastname, String username,Status status) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.status = status;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getUsername() {
		return username;
	}

	public Status getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return firstname + ", " + lastname + ", " + username + ", " + status;
	}

}
