package com.medialab.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "CONTENT")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.medialab.persistence.entity.Content")
public class Content implements Serializable
{

	private static final long serialVersionUID = 1228100433287678371L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PLAYER_CLUB")
	private String playerClub;

	@Column(name = "CLUB_URL")
	private String clubUrl;

	@Column(name = "PLAYER_POSITION")
	private String playerPosition;

	@Column(name = "TEXT")
	private String text;

	public Content()
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the playerClub
	 */
	public String getPlayerClub()
	{
		return playerClub;
	}

	/**
	 * @param playerClub
	 *            the playerClub to set
	 */
	public void setPlayerClub(String playerClub)
	{
		this.playerClub = playerClub;
	}

	/**
	 * @return the clubUrl
	 */
	public String getClubUrl()
	{
		return clubUrl;
	}

	/**
	 * @param clubUrl
	 *            the clubUrl to set
	 */
	public void setClubUrl(String clubUrl)
	{
		this.clubUrl = clubUrl;
	}

	/**
	 * @return the playerPosition
	 */
	public String getPlayerPosition()
	{
		return playerPosition;
	}

	/**
	 * @param playerPosition
	 *            the playerPosition to set
	 */
	public void setPlayerPosition(String playerPosition)
	{
		this.playerPosition = playerPosition;
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}

}
