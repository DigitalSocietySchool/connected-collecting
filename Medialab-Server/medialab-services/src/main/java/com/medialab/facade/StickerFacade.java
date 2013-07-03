package com.medialab.facade;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.StickerResult;
import com.medialab.dto.StickerDTO;
import com.medialab.factory.StickerFactory;
import com.medialab.persistence.entity.Sticker;
import com.medialab.services.StickerService;

@Component
public class StickerFacade
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StickerFacade.class);
	
	@Autowired
	private StickerService stickerService;
	
	public StickerResult getDoublesForAnotherUser(Long userId, Long anotherUser)
	{
		StickerResult result = new StickerResult();
		result.setResponse(MediaLABResponse.USER_NOT_FOUND);
		try
		{
			List<Sticker> stickers = stickerService.getDoublesForAnotherUser(userId, anotherUser);
			if (stickers != null)
			{
				result.setResponse(MediaLABResponse.SUCCESS);
				List<StickerDTO> dtos = StickerFactory.createDTOsFromSticker(stickers);
				result.setStickers(dtos);
			}
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving users with repeated stickers", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
}
