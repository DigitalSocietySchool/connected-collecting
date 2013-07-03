package com.medialab.factory;

import java.util.ArrayList;
import java.util.List;

import com.medialab.dto.AlbumDTO;
import com.medialab.persistence.entity.Album;

public class AlbumFactory {
	private AlbumFactory()
	{
		
	}
	
	public static Album createAlbumFromDTO(AlbumDTO dto)
	{
		if (dto != null)
		{
			Album album = new Album();
			album.setId(dto.getId());
			album.setName(dto.getName());
			return album;
		}
		return null;
	}
	
	public static AlbumDTO createDTOFromAlbum(Album album)
	{
		if (album != null)
		{
			AlbumDTO dto = new AlbumDTO();
			dto.setId(album.getId());
			dto.setName(album.getName());
			return dto;
		}
		return null;
	}
	
	public static List<Album> createAlbumsFromDTOs(List<AlbumDTO> dtos) {
		List<Album> albums = new ArrayList<Album>();
		if (dtos != null) {
			for (AlbumDTO dto : dtos) {
				albums.add(createAlbumFromDTO(dto));
			}
		}
		return albums;
	}
	
	public static List<AlbumDTO> createDTOsFromAlbums(List<Album> albums) {
		List<AlbumDTO> dtos = new ArrayList<AlbumDTO>();
		if (albums != null) {
			for (Album dto : albums) {
				dtos.add(createDTOFromAlbum(dto));
			}
		}
		return dtos;
	}	
}
