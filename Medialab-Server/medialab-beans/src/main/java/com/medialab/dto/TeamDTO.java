package com.medialab.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TeamDTO
{

	private Long id;

	private String description;

	private String color;

	private int stickerCount;

	private AlbumDTO album;

	private List<PlayerDTO> players;

	private String name;

	public TeamDTO()
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
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the color
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color)
	{
		this.color = color;
	}

	/**
	 * @return the stickerCount
	 */
	public int getStickerCount()
	{
		return stickerCount;
	}

	/**
	 * @param stickerCount
	 *            the stickerCount to set
	 */
	public void setStickerCount(int stickerCount)
	{
		this.stickerCount = stickerCount;
	}

	/**
	 * @return the album
	 */
	public AlbumDTO getAlbum()
	{
		return album;
	}

	/**
	 * @param album
	 *            the album to set
	 */
	public void setAlbum(AlbumDTO album)
	{
		this.album = album;
	}

	/**
	 * @return the players
	 */
	public List<PlayerDTO> getPlayers()
	{
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(List<PlayerDTO> players)
	{
		this.players = players;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

}
