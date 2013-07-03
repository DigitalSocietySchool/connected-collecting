package com.medialab.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.VideoResult;
import com.medialab.dto.VideoDTO;
import com.medialab.factory.VideoFactory;
import com.medialab.persistence.entity.Video;
import com.medialab.services.VideoService;

@Component
public class VideoFacade
{
	private static final Logger LOGGER = LoggerFactory.getLogger(VideoFacade.class);
	
	@Autowired
	private VideoService service;
	
	public VideoResult getVideo(Long videoId)
	{
		VideoResult result = new VideoResult();
		result.setResponse(MediaLABResponse.VIDEO_NOT_FOUND);
		try
		{
			Video video = service.findOne(videoId);
			if (video != null)
			{
				VideoDTO dto = VideoFactory.createDTOFromVideo(video);
				result.setResponse(MediaLABResponse.SUCCESS);
				result.setDto(dto);
			}
		}
		catch (Exception ex)
		{
			LOGGER.error("Error retrieving teams.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
}
