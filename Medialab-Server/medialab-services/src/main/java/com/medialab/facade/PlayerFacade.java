package com.medialab.facade;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.PlayerResult;
import com.medialab.dto.PlayerDTO;
import com.medialab.dto.TeamDTO;
import com.medialab.factory.PlayerFactory;
import com.medialab.factory.TeamFactory;
import com.medialab.persistence.entity.Player;
import com.medialab.services.PlayerService;
import com.medialab.services.TeamService;

@Component
public class PlayerFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerFacade.class);
	
	@Autowired
	private PlayerService service;
	
	@Autowired
	private TeamService teamService;
	
	public PlayerResult getByName(String name)
	{
		PlayerResult result = new PlayerResult();
		try
		{
			Player player = service.getByName(name);
			return createResult(player);
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving players.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
	
	public PlayerResult getByStickerId(Long stickerId)
	{
		PlayerResult result = new PlayerResult();
		try
		{
			Player player = service.getByStickerId(stickerId);
			player.getTeam().getPlayers().clear();
			return createResult(player);
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving players.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
	
	public PlayerResult getByStickerNumber(String number)
	{
		PlayerResult result = new PlayerResult();
		try
		{
			Player player = service.getByStickerNumber(number);
			return createResult(player);
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving players.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
	
	public PlayerResult getByTeamId(Long teamId)
	{
		PlayerResult result = new PlayerResult();
		result.setResponse(MediaLABResponse.PLAYER_NOT_FOUND);
		try
		{
			List<Player> players = service.getByTeamId(teamId);
			if (players != null)
			{
				TeamDTO teamDTO = TeamFactory.createDTOFromTeam(teamService.findOne(teamId));
				List<PlayerDTO> dto = PlayerFactory.createDTOsFromPlayers(players, teamDTO);
				result.setPlayers(dto);
				result.setResponse(MediaLABResponse.SUCCESS);
			}
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving players.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}
	
	public PlayerResult createResult(Player player)
	{
		PlayerResult result = new PlayerResult();
		result.setResponse(MediaLABResponse.PLAYER_NOT_FOUND);
		if (player != null)
		{
			TeamDTO teamDTO = TeamFactory.createDTOFromTeam(player.getTeam());
			PlayerDTO dto = PlayerFactory.createDTOFromPlayer(player);
			dto.setTeam(teamDTO);
			result.setResponse(MediaLABResponse.SUCCESS);
			result.setPlayers(createPlayers(dto));
		}
		return result;
	}
	
	
	private List<PlayerDTO> createPlayers(PlayerDTO dto)
	{
		List<PlayerDTO> dtos = new ArrayList<PlayerDTO>();
		dtos.add(dto);
		return dtos;
	}
	
}
