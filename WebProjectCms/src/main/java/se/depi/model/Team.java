package se.depi.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Team extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "team")
	private Collection<User> users;

	protected Team() {
	}

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name; 
	}

}
