package se.depi.resource;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.depi.model.Team;
import se.depi.service.EntityService;

@Component
@Path("/teams")
public class TeamResource {

	@Context
	private UriInfo uriInfo;
	private final EntityService service;

	@Autowired
	public TeamResource(EntityService service) {
		this.service = service;
	}

	@POST
	public Response addTeam(Team team) {
		service.createTeam(team);
		URI location = uriInfo.getAbsolutePathBuilder().path(team.getId().toString()).build();
		return Response.created(location).build();
	}

	@GET
	public Response getAllTeams() {
		return Response.ok(service.findAllTeams()).build();
	}
	@GET
	@Path("{id}")
	public Response findTeamById(@PathParam("id") Long id){
		return Response.ok(service.findTeam(id)).build();
	}
	
	@PUT
	@Path("{id}")
	public Response updateTeam(@PathParam("id") Long id, String name){
		Team team = service.findTeam(id);
		team.setName(name);
		team = service.createTeam(team);
		
		return Response.noContent().build();
	}

}
