package com.medialab.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.ConnectorConstants;
import com.medialab.beans.TeamResult;
import com.medialab.facade.TeamFacade;

@Component
@Path(ConnectorConstants.ALBUM_RESOURCE_NAME)
@Produces({ MediaType.APPLICATION_JSON })
public class AlbumResource {
	
	@Autowired
	private TeamFacade facade;

	@GET
	@Path(ConnectorConstants.GET_TEAMS_NAME)
	public TeamResult getTeams()
	{
		return facade.getTeams();
	}
	
	@GET
	@Path(ConnectorConstants.GET_TEAM_NAME + "/{" + ConnectorConstants.TEAM_ID_NAME + "}")
	public TeamResult getTeam(@PathParam(ConnectorConstants.TEAM_ID_NAME) Long id)
	{
		return facade.getTeam(id);
	}
}
