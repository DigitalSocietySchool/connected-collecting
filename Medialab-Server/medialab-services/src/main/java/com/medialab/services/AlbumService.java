package com.medialab.services;

import org.springframework.stereotype.Component;

import com.medialab.persistence.entity.Album;
import com.medialab.services.generic.GenericService;

@Component
public class AlbumService extends GenericService<Album, Long> {

	public AlbumService() {
		super.setClazz(Album.class);
	}

}
