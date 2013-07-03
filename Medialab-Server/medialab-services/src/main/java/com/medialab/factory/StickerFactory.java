package com.medialab.factory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import com.medialab.dto.StickerDTO;
import com.medialab.persistence.entity.Sticker;

public class StickerFactory
{

	private StickerFactory()
	{
	}

	public static Sticker createStickerFromDTO(StickerDTO dto)
	{
		if (dto != null)
		{
			Sticker sticker = new Sticker();
			sticker.setId(dto.getId());
			sticker.setNumber(dto.getNumber());
			sticker.setImage(dto.getImage());
			sticker.setType(dto.getType());
			sticker.setContent(ContentFactory.createContentsFromDTOs(dto.getContent()));
			return sticker;
		}
		return null;
	}

	public static StickerDTO createDTOFromSticker(Sticker sticker)
	{
		if (sticker != null)
		{
			StickerDTO dto = new StickerDTO();
			dto.setId(sticker.getId());
			dto.setNumber(sticker.getNumber());
			dto.setImage(sticker.getImage());
			dto.setType(sticker.getType());
			if (Hibernate.isInitialized(sticker.getContent()))
			{
				dto.setContent(ContentFactory.createDTOsFromContents(sticker.getContent()));
			}
			return dto;
		}
		return null;
	}

	public static List<Sticker> createStickersFromDTO(List<StickerDTO> dtos)
	{
		List<Sticker> stickers = new ArrayList<Sticker>();
		for (StickerDTO dto : dtos)
		{
			stickers.add(createStickerFromDTO(dto));
		}
		return stickers;
	}

	public static List<StickerDTO> createDTOsFromSticker(List<Sticker> stickers)
	{
		List<StickerDTO> dtos = new ArrayList<StickerDTO>();
		for (Sticker sticker : stickers)
		{
			dtos.add(createDTOFromSticker(sticker));
		}
		return dtos;
	}
}
