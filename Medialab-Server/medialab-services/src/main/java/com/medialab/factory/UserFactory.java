package com.medialab.factory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import com.medialab.dto.UserDTO;
import com.medialab.persistence.entity.Sticker;
import com.medialab.persistence.entity.User;

public class UserFactory
{

	private UserFactory()
	{
	}

	public static User createUserFromDTO(UserDTO dto)
	{
		if (dto != null)
		{
			User user = new User();
			user.setId(dto.getId());
			user.setName(dto.getName());
			user.setAvatarUrl(dto.getAvatarUrl());
			user.setCreationDate(dto.getCreationDate());
			user.setLastLoginDate(dto.getLastLoginDate());
			user.setRank(RankFactory.createRankFromDTO(dto.getRank()));
			user.setStickers(StickerFactory.createStickersFromDTO(dto.getStickers()));
			return user;
		}
		return null;
	}

	public static UserDTO createDTOFromUser(User user, int stickersNextLevel, int stickerCount)
	{
		if (user != null)
		{
			UserDTO dto = new UserDTO();
			dto.setId(user.getId());
			dto.setName(user.getName());
			dto.setAvatarUrl(user.getAvatarUrl());
			dto.setCreationDate(user.getCreationDate());
			dto.setLastLoginDate(user.getLastLoginDate());
			dto.setRank(RankFactory.createDTOFromRank(user.getRank()));
			List<Sticker> stickers = user.getStickers();
			if (stickers!= null && Hibernate.isInitialized(stickers))
			{
				dto.setStickers(StickerFactory.createDTOsFromSticker(user.getStickers()));
			}
			dto.setStickersNextLevel(stickersNextLevel);
			dto.setStickerCount(stickerCount);
			return dto;
		}
		return null;
	}
	
	public static List<UserDTO> createDTOsFromUsers(List<User> users)
	{
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		if (users != null)
		{
			for (User dto : users)
			{
				dtos.add(createDTOFromUser(dto, 0, 0));
			}
		}
		return dtos;
	}
	
}
