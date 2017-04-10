package se.depi.resource;

import java.net.URI;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
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

import se.depi.model.Status;
import se.depi.model.WorkItem;
import se.depi.params.WorkItemRequestBean;
import se.depi.service.EntityService;

@Component
@Path("/workitems")
public class WorkItemResource {

	@Context
	private UriInfo uriInfo;

	private EntityService service;

	@Autowired
	public WorkItemResource(EntityService service) {
		this.service = service;
	}

	@POST
	public Response createWorkItem(WorkItem item) {
		WorkItem workItem = service.createItem(item);
		URI location = uriInfo.getAbsolutePathBuilder().path(workItem.getId().toString()).build();
		return Response.created(location).build();
	}

	@GET 
	@Path("status/{status}")
	public Response getByStatus(@PathParam("status") Status status) {
		return Response.ok(service.findByStatus(status)).build();
	}

	@GET
	@Path("teams")
	public Response getItemsInTeam(@BeanParam WorkItemRequestBean request) {
		return Response.ok(service.findItemsInTeam(request.getId())).build();
	}

	@GET
	@Path("users")
	public Response getItemsFromUser(@BeanParam WorkItemRequestBean request) {
		return Response.ok(service.findItemsByUser(request.getUserId())).build();
	}

	@GET
	@Path("description")
	public Response findItemsContaining(@BeanParam WorkItemRequestBean request) {
		return Response.ok(service.findItemsByDescription(request.getDescription())).build();
	}

	@PUT
	@Path("user/{id}/{userId}")
	public Response assignItem(@PathParam("id") Long itemId,@PathParam("userId") Long userId) {
		service.assignWorkItem(itemId, userId);
		return Response.ok().build();

	}
	
	@PUT
	@Path("status/{id}")
	public Response changeStatus(@PathParam("id") Long id, Status status) {
		WorkItem item = service.findWorkItem(id);
		item.setStatus(status);
		service.createItem(item);
		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response removeWorkItem(@PathParam("id") Long id) {
		service.removeWorkItem(id);
		return Response.noContent().build();

	}

}
