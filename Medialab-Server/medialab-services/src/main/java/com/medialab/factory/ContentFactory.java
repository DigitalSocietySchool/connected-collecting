package com.medialab.factory;

import java.util.ArrayList;
import java.util.List;

import com.medialab.dto.ContentDTO;
import com.medialab.persistence.entity.Content;

public class ContentFactory
{
	private ContentFactory()
	{
		
	}
	
	public static Content createContentFromDTO(ContentDTO dto)
	{
		if (dto != null)
		{
			Content content = new Content();
			content.setId(dto.getId());
			content.setClubUrl(dto.getClubUrl());
			content.setPlayerClub(dto.getPlayerClub());
			content.setPlayerPosition(dto.getPlayerPosition());
			content.setText(dto.getText());
			return content;
		}
		return null;
	}
	
	public static ContentDTO createDTOFromContent(Content content)
	{
		if (content != null)
		{
			ContentDTO dto = new ContentDTO();
			dto.setId(content.getId());
			dto.setClubUrl(content.getClubUrl());
			dto.setPlayerClub(content.getPlayerClub());
			dto.setPlayerPosition(content.getPlayerPosition());
			dto.setText(content.getText());
			return dto;
		}
		return null;
	}
	
	public static List<Content> createContentsFromDTOs(List<ContentDTO> dtos) {
		List<Content> albums = new ArrayList<Content>();
		if (dtos != null) {
			for (ContentDTO dto : dtos) {
				albums.add(createContentFromDTO(dto));
			}
		}
		return albums;
	}
	
	public static List<ContentDTO> createDTOsFromContents(List<Content> contents) {
		List<ContentDTO> dtos = new ArrayList<ContentDTO>();
		if (contents != null) {
			for (Content dto : contents) {
				dtos.add(createDTOFromContent(dto));
			}
		}
		return dtos;
	}	
}
