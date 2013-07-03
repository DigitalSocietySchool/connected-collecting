package com.medialab.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CacheModeType;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.medialab.beans.RankLevel;

@Entity
@Table(name = "RANK")
@NamedQueries({
	@NamedQuery(name = "Rank.getNextLevel", query = "SELECT r FROM Rank r WHERE r.level = ?", cacheable = true, cacheMode = CacheModeType.NORMAL, cacheRegion = "com.medialab.persistence.entity.Rank"),
})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.medialab.persistence.entity.Rank")
public class Rank implements Serializable
{
	private static final long serialVersionUID = 5428109690585374268L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ALIAS")
	private String alias;

	@Column(name = "LEVEL")
	@Enumerated
	private RankLevel level;

	@Column(name = "GOAL")
	private int goal;

	@Column(name = "BADGE_URL")
	private String badgeUrl;

	public Rank()
	{
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param level the level to set
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
	public String getBadgeUrl()
	{
		return badgeUrl;
	}

	/**
	 * @param badgeUrl
	 *            the badgeUrl to set
	 */
	public void setBadgeUrl(String badgeUrl)
	{
		this.badgeUrl = badgeUrl;
	}

}
