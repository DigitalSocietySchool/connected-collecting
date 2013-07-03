package com.medialab.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.ConnectorConstants;
import com.medialab.beans.VideoResult;
import com.medialab.facade.VideoFacade;

@Component
@Path(ConnectorConstants.VIDEO_RESOURCE_NAME)
@Produces({ MediaType.APPLICATION_JSON })
public class VideoResource
{
	@Autowired
	private VideoFacade facade;
	
	@GET
	@Path("{" + ConnectorConstants.VIDEO_ID_NAME + "}")
	public VideoResult getVideo(@PathParam(ConnectorConstants.VIDEO_ID_NAME) Long videoId)
	{
		return facade.getVideo(videoId);
	}
}
