package com.medialab.beans;

public enum RankLevel
{
	BEGINNER(0), AMATEUR(1), LEAGUE(2), WORLD_CLASS(3);

	private int level;

	private RankLevel(int level)
	{
		this.level = level;
	}

	/**
	 * @return the level
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

	public static RankLevel getNextLevel(RankLevel level)
	{
		if (level != null)
		{
			RankLevel[] ranks = RankLevel.values();
			for (RankLevel rank : ranks)
			{
				if (rank.getLevel() >= level.getLevel())
				{
					return rank;
				}
			}
		}
		return RankLevel.BEGINNER;
	}
	
	public static boolean isLastLevel(RankLevel level)
	{
		return RankLevel.WORLD_CLASS.equals(level);
	}
}
