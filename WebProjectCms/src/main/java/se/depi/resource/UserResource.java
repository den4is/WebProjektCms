package se.depi.resource;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.depi.model.Team;
import se.depi.model.User;
import se.depi.params.UserRequestBean;
import se.depi.service.EntityService;

@Component
@Path("/users")
public final class UserResource {

	private final EntityService service;

	@Context
	private UriInfo uriInfo;

	@Autowired
	public UserResource(EntityService service) {
		this.service = service;
	}

	@POST
	public Response addUser(User user) {
		service.addUser(user);
		URI location = uriInfo.getAbsolutePathBuilder().path(user.getId().toString()).build();
		return Response.created(location).build();

	}

	@PUT
	@Path("{id}")
	public Response addUserToTeam(@PathParam("id") Long id, Long teamId) {
		Team team = service.findTeam(teamId);
		service.addUserToTeam(id, team.getId());
		return Response.ok().build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findUser(@PathParam("id") Long id) {
		return Response.ok(service.findUser(id)).build();
	}
	
	@GET
	@Path("param")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findUser(@BeanParam UserRequestBean request){
		Collection<User> users = service.findByFirstname(request.getFirstname());
		return Response.ok(users).build();
	}
	
	@GET
	@Path("team/{id}")
	public Response findUsersInTeam(@PathParam("id")Long teamId){
		Collection<User> users = service.findUsersInTeam(teamId);
		return Response.ok(users).build();
	}

}
