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

import se.depi.model.Issue;
import se.depi.model.WorkItem;
import se.depi.service.EntityService;

@Component
@Path("/issues")
public class IssueResource {

	@Context
	private UriInfo uriInfo;

	private EntityService service;

	@Autowired
	public IssueResource(EntityService service) {
		this.service = service;
	}

	@POST
	@Path("{id}")
	public Response createIssue(Issue issue, @PathParam("id") Long itemId) {
		Issue dbIssue = service.saveAndUpdateIssue(issue);
		WorkItem item = service.findWorkItem(itemId);
		service.assignIssue(dbIssue.getId(), item.getId());
		URI location = uriInfo.getAbsolutePathBuilder().path(dbIssue.getId().toString()).build();
		return Response.created(location).build();
	}

	@GET
	@Path("items")
	public Response getIssuedItems() {
		return Response.ok(service.findIssuedItems()).build();
	}

	@PUT
	@Path("{id}")
	public Response updateIssue(@PathParam("id") Long id, Issue webIssue) {
		Issue issue = service.getIssueById(id);
		issue = service.saveAndUpdateIssue(issue);
		return Response.ok().build();
	}

}
