package com.medialab.factory;

import com.medialab.dto.VideoDTO;
import com.medialab.persistence.entity.Video;

public class VideoFactory
{
	public static Video createVideoFromDTO(VideoDTO dto)
	{
		if (dto != null)
		{
			Video video = new Video();
			video.setId(dto.getId());
			video.setUrl(dto.getUrl());
			return video;
		}
		return null;
	}

	public static VideoDTO createDTOFromVideo(Video video)
	{
		if (video != null)
		{
			VideoDTO dto = new VideoDTO();
			dto.setId(video.getId());
			dto.setUrl(video.getUrl());
			return dto;
		}
		return null;
	}
}
