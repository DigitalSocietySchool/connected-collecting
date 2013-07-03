package com.medialab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.medialab.beans.RankLevel;
import com.medialab.persistence.entity.Rank;
import com.medialab.services.generic.GenericService;

@Component
public class RankService extends GenericService<Rank, Long>
{

	public RankService()
	{
		super.setClazz(Rank.class);
	}
	
	public int calculateStickersNeeded(Rank userRank, int userStickers)
	{
		RankLevel userLevel = userRank.getLevel();
		if (!RankLevel.isLastLevel(userLevel))
		{
			RankLevel nextLevel = RankLevel.getNextLevel(userLevel);
			String namedQuery = "Rank.getNextLevel";
			List<Object> parameters = super.createParameterList(nextLevel);
			Rank rank = super.getByNamedQuery(namedQuery, parameters);
			return (rank.getGoal() - userStickers);
		}
		return 0;
	}

}
