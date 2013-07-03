package com.medialab.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerDTO {

	private Long id;

	private TeamDTO team;

	private String name;

	private StickerDTO sticker;

	public PlayerDTO() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the team
	 */
	public TeamDTO getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(TeamDTO team) {
		this.team = team;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sticker
	 */
	public StickerDTO getSticker() {
		return sticker;
	}

	/**
	 * @param sticker
	 *            the sticker to set
	 */
	public void setSticker(StickerDTO sticker) {
		this.sticker = sticker;
	}

}
