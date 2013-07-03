package com.medialab.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {
	private Long id;
	
	private String name;
	
	private String avatarUrl;
	
	private Date creationDate;
	
	private Date lastLoginDate;
	
	private RankDTO rank;
	
	private int stickersNextLevel;
	
	private int stickerCount;
	
	private List<StickerDTO> stickers;

	public UserDTO() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the avatarUrl
	 */
	@XmlElement(name = "avatar_url")
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the rank
	 */
	public RankDTO getRank()
	{
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(RankDTO rank)
	{
		this.rank = rank;
	}

	/**
	 * @return the stickersNextLevel
	 */
	public int getStickersNextLevel()
	{
		return stickersNextLevel;
	}

	/**
	 * @param stickersNextLevel the stickersNextLevel to set
	 */
	public void setStickersNextLevel(int stickersNextLevel)
	{
		this.stickersNextLevel = stickersNextLevel;
	}

	/**
	 * @return the stickerCount
	 */
	@XmlElement(name = "sticker_count")
	public int getStickerCount()
	{
		return stickerCount;
	}

	/**
	 * @param stickerCount the stickerCount to set
	 */
	public void setStickerCount(int stickerCount)
	{
		this.stickerCount = stickerCount;
	}

	/**
	 * @return the stickers
	 */
	public List<StickerDTO> getStickers() {
		return stickers;
	}

	/**
	 * @param stickers the stickers to set
	 */
	public void setStickers(List<StickerDTO> stickers) {
		this.stickers = stickers;
	}
	
	
}
