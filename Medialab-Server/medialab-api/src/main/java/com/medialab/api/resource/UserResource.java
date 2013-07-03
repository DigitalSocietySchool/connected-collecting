package com.medialab.api.resource;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.ConnectorConstants;
import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.StickerResult;
import com.medialab.beans.UserResult;
import com.medialab.facade.StickerFacade;
import com.medialab.facade.UserFacade;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path(ConnectorConstants.USER_RESOURCE_NAME)
@Produces({ MediaType.APPLICATION_JSON })
public class UserResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	private UserFacade facade;
	
	@Autowired
	private StickerFacade stickerFacade;
	
	@GET
	@Path(ConnectorConstants.GET_USER_STICKERS_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{" + ConnectorConstants.TEAM_ID_NAME + "}")
	public UserResult getStickers(@PathParam(ConnectorConstants.USER_ID_NAME) Long userId, @PathParam(ConnectorConstants.TEAM_ID_NAME) Long teamId)
	{
		return facade.getUserStickers(userId, teamId);
	}
	
	@POST
	@Path(ConnectorConstants.ADD_STICKER_NAME)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public UserResult addSticker(@FormDataParam(ConnectorConstants.FILE_NAME) InputStream stream, @FormDataParam(ConnectorConstants.USER_ID_NAME) Long userId)
	{
		return facade.addSticker(stream, userId);
	}
	
	@GET
	@Path(ConnectorConstants.GET_USER_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}")
	public UserResult getById(@PathParam(ConnectorConstants.USER_ID_NAME) Long id)
	{
		return facade.getById(id);
	}
	
	@GET
	@Path(ConnectorConstants.GET_USERS_FOR_TRADE_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{" + ConnectorConstants.STICKER_ID_NAME + "}")
	public UserResult getUserForTrade(@PathParam(ConnectorConstants.USER_ID_NAME) Long userId, 
			@PathParam(ConnectorConstants.STICKER_ID_NAME) Long stickerId)
	{
		return facade.getUserForTrade(userId, stickerId);
	}
	
	@GET
	@Path(ConnectorConstants.GET_DOUBLES_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{" + ConnectorConstants.ANOTHER_USER_ID_NAME + "}")
	public StickerResult getDoublesForAnotherUser(@PathParam(ConnectorConstants.USER_ID_NAME) Long userId, 
			@PathParam(ConnectorConstants.ANOTHER_USER_ID_NAME) Long anotherUser)
	{
		return stickerFacade.getDoublesForAnotherUser(userId, anotherUser);
	}
	
	@GET
	@Path(ConnectorConstants.CLEAN_DB_NAME)
	public Response cleanDB()
	{
		MediaLABResponse r = facade.cleanDB();
		LOGGER.info(String.format("Cleaned DB, response[%s]", r));
        InputStream is = getClass().getResourceAsStream("/success.html");
        return Response.ok(is, MediaType.TEXT_HTML).build();
	}
	
	@GET
	public String test()
	{
		return "Sucess";
	}
	
}
