package com.medialab.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.dto.UserDTO;

@XmlRootElement
public class UserResult {
	private MediaLABResponse response;

	private List<UserDTO> users;
	
	//temporary solution, for the android app
	private String stickerName;

	public UserResult() {
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
	 * @return the users
	 */
	public List<UserDTO> getUsers()
	{
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<UserDTO> users)
	{
		this.users = users;
	}

	/**
	 * @return the stickerName
	 */
	public String getStickerName()
	{
		return stickerName;
	}

	/**
	 * @param stickerName the stickerName to set
	 */
	public void setStickerName(String stickerName)
	{
		this.stickerName = stickerName;
	}

}
