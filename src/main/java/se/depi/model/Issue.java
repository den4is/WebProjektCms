package se.depi.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Issue extends AbstractEntity {

	private String name;
	private String description;
	
	@OneToOne(mappedBy = "issue")
	@JoinColumn(unique = true)
	private WorkItem item;
	
	protected Issue(){}

	public Issue(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
