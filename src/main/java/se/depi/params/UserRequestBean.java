package se.depi.params;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class UserRequestBean {

	@QueryParam("firstname")
	@DefaultValue("")
	private String firstname;
	@QueryParam("lastname")
	@DefaultValue("")
	private String lastname;
	@QueryParam("username")
	@DefaultValue("")
	private String username;

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getUsername() {
		return username;
	}
}
