package com.medialab.factory;

import com.medialab.dto.RankDTO;
import com.medialab.persistence.entity.Rank;

public class RankFactory
{
	private RankFactory()
	{
		
	}
	
	public static Rank createRankFromDTO(RankDTO dto)
	{
		if (dto != null)
		{
			Rank rank = new Rank();
			rank.setId(dto.getId());
			rank.setAlias(dto.getAlias());
			rank.setBadgeUrl(dto.getBadgeUrl());
			rank.setGoal(dto.getGoal());
			rank.setLevel(dto.getLevel());
			return rank;
		}
		return null;
	}
	
	public static RankDTO createDTOFromRank(Rank rank)
	{
		if (rank != null)
		{
			RankDTO dto = new RankDTO();
			dto.setId(rank.getId());
			dto.setAlias(rank.getAlias());
			dto.setBadgeUrl(rank.getBadgeUrl());
			dto.setGoal(rank.getGoal());
			dto.setLevel(rank.getLevel());
			return dto;
		}
		return null;
	}
}
