package com.medialab.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContentDTO
{
	private Long id;

	private String playerClub;

	private String clubUrl;

	private String playerPosition;

	private String text;

	public ContentDTO()
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
