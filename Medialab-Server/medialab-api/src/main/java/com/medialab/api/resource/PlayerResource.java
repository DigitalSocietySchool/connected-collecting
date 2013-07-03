package com.medialab.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.ConnectorConstants;
import com.medialab.beans.PlayerResult;
import com.medialab.facade.PlayerFacade;

@Component
@Path(ConnectorConstants.PLAYER_RESOURCE_NAME)
@Produces({ MediaType.APPLICATION_JSON })
public class PlayerResource {
	
	@Autowired
	private PlayerFacade facade;
	
	@GET
	@Path(ConnectorConstants.GET_BY_NAME + "/{" + ConnectorConstants.PLAYER_NAME + "}")
	public PlayerResult getByName(@PathParam(ConnectorConstants.PLAYER_NAME) String name)
	{
		return facade.getByName(name);
	}
	
	@GET
	@Path(ConnectorConstants.GET_BY_STICKER_ID_NAME + "/{" + ConnectorConstants.STICKER_ID_NAME + "}")
	public PlayerResult getByStickerId(@PathParam(ConnectorConstants.STICKER_ID_NAME) Long stickerId)
	{
		return facade.getByStickerId(stickerId);
	}
	
	@GET
	@Path(ConnectorConstants.GET_BY_STICKER_NUMBER_NAME + "/{" + ConnectorConstants.NUMBER_NAME + "}")
	public PlayerResult getByStickerNumber(@PathParam(ConnectorConstants.NUMBER_NAME) String number)
	{
		return facade.getByStickerNumber(number);
	}
	
	@GET
	@Path(ConnectorConstants.GET_BY_TEAM_ID_NAME + "/{" + ConnectorConstants.TEAM_ID_NAME + "}")
	public PlayerResult getByTeamId(@PathParam(ConnectorConstants.TEAM_ID_NAME) Long teamId)
	{
		return facade.getByTeamId(teamId);
	}
}
