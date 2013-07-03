package com.medialab.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.dto.TeamDTO;

@XmlRootElement
public class TeamResult
{

	private MediaLABResponse response;

	private List<TeamDTO> teams;

	public TeamResult()
	{
	}

	/**
	 * @return the response
	 */
	public MediaLABResponse getResponse()
	{
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(MediaLABResponse response)
	{
		this.response = response;
	}

	/**
	 * @return the teams
	 */
	public List<TeamDTO> getTeams()
	{
		return teams;
	}

	/**
	 * @param teams
	 *            the teams to set
	 */
	public void setTeams(List<TeamDTO> teams)
	{
		this.teams = teams;
	}

}
