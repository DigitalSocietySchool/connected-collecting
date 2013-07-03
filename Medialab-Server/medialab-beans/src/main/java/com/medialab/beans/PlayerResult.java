package com.medialab.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.dto.PlayerDTO;

@XmlRootElement
public class PlayerResult {
	private MediaLABResponse response;

	private List<PlayerDTO> players;

	public PlayerResult() {
	}

	/**
	 * @return the response
	 */
	public MediaLABResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(MediaLABResponse response) {
		this.response = response;
	}

	/**
	 * @return the players
	 */
	public List<PlayerDTO> getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(List<PlayerDTO> players) {
		this.players = players;
	}

}
