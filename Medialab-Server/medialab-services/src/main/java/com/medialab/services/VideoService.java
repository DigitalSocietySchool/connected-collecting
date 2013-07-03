package com.medialab.services;

import org.springframework.stereotype.Component;

import com.medialab.persistence.entity.Video;
import com.medialab.services.generic.GenericService;

@Component
public class VideoService extends GenericService<Video, Long>
{
	public VideoService() {
		super.setClazz(Video.class);
	}
}
