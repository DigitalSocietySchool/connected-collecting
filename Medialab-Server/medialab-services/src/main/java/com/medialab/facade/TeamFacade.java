package com.medialab.facade;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.TeamResult;
import com.medialab.dto.TeamDTO;
import com.medialab.factory.TeamFactory;
import com.medialab.persistence.entity.Team;
import com.medialab.services.TeamService;

@Component
public class TeamFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamFacade.class);

	@Autowired
	private TeamService teamService;
	
	public TeamResult getTeams()
	{
		TeamResult result = new TeamResult();
		result.setResponse(MediaLABResponse.ALBUM_NOT_FOUND);
		try
		{
			List<Team> teams = teamService.getTeams();
			if (teams != null)
			{
				List<TeamDTO> dtos = TeamFactory.createDTOsFromTeams(teams);
				
				result.setResponse(MediaLABResponse.SUCCESS);
				result.setTeams(dtos);
			}
		}
		catch (Exception ex)
		{
			LOGGER.error("Error retrieving teams.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
	
	public TeamResult getTeam(Long id)
	{
		TeamResult result = new TeamResult();
		result.setResponse(MediaLABResponse.ALBUM_NOT_FOUND);
		try
		{
			Team team = teamService.getTeam(id);
			if (team != null)
			{
				TeamDTO dto = TeamFactory.createDTOFromTeam(team);
				List<TeamDTO> dtos = new ArrayList<TeamDTO>();
				dtos.add(dto);
				
				result.setResponse(MediaLABResponse.SUCCESS);
				result.setTeams(dtos);
			}
		}
		catch (Exception ex)
		{
			LOGGER.error("Error retrieving teams.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
	
}
