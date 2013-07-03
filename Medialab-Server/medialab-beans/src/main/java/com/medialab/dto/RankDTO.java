package com.medialab.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.beans.RankLevel;

@XmlRootElement
public class RankDTO
{
	private Long id;
	
	private String alias;

	private RankLevel level;
	
	private int goal;
	
	private String badgeUrl;

	public RankDTO()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the alias
	 */
	public String getAlias()
	{
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	/**
	 * @return the level
	 */
	public RankLevel getLevel()
	{
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(RankLevel level)
	{
		this.level = level;
	}

	/**
	 * @return the goal
	 */
	public int getGoal()
	{
		return goal;
	}

	/**
	 * @param goal
	 *            the goal to set
	 */
	public void setGoal(int goal)
	{
		this.goal = goal;
	}

	/**
	 * @return the badgeUrl
	 */
	@XmlElement(name = "badge_url")
	public String getBadgeUrl()
	{
		return badgeUrl;
	}

	/**
	 * @param badgeUrl the badgeUrl to set
	 */
	public void setBadgeUrl(String badgeUrl)
	{
		this.badgeUrl = badgeUrl;
	}

}
