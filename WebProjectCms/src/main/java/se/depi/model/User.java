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
	/*@Enumerated
	In our User entity, we add a rating field of the enum type Rating and annotate it 
	with @Enumerated(EnumType.STRING) to declare that its value should be converted from what
	is effectively a String in the database to the Rating type. Converts from JPA 
	
	*It can sometimes be desirable to have a Java enum type to represent a particular column in a database.
	* JPA supports converting database data to and from Java enum types via the @javax.persistence.
	* Enumerated annotation.
	*
	*eftersom vi gör private status status och ska använda enums variablerna i databasen så har vi därför
	*annoterat det med @enumerated(enimtype.string) för att då blir det able to convert
	*/

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
