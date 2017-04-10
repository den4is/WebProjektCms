		package se.depi.params;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class WorkItemRequestBean {

	@QueryParam("inTeam")
	@DefaultValue("0")
	private Long teamId;
	@QueryParam("withUser")
	@DefaultValue("0")
	private Long userId;
	@QueryParam("like")
	@DefaultValue("")
	private String description;

	public Long getId() {
		return teamId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getDescription() {
		return description;
	}

}
