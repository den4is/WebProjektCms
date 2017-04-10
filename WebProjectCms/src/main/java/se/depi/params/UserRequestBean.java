package se.depi.params;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class UserRequestBean {

	/*
	 * @QueryParam = /users/query?url=mkyong.com
	 * In above URI pattern, query parameter is “url=mkyong.com“
	 * and you can get the url value with @QueryParam("url").
	 * Queryparam = argumentet och svaret. aka url = mkyongblabla.com
	 */

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
