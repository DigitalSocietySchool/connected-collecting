package com.medialab.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import com.medialab.dto.PlayerDTO;
import com.medialab.dto.TeamDTO;
import com.medialab.persistence.entity.Player;
import com.medialab.persistence.entity.Team;

public class PlayerFactory
{
	private PlayerFactory()
	{

	}

	public static Player createPlayerFromDTO(PlayerDTO dto, Team team)
	{
		if (dto != null)
		{
			Player player = new Player();
			player.setId(dto.getId());
			player.setName(dto.getName());
			player.setSticker(StickerFactory.createStickerFromDTO(dto.getSticker()));
			player.setTeam(team);
			return player;
		}
		return null;
	}

	public static PlayerDTO createDTOFromPlayer(Player player)
	{
		if (player != null)
		{
			PlayerDTO dto = new PlayerDTO();
			dto.setId(player.getId());
			dto.setName(player.getName());
			if (Hibernate.isInitialized(player.getSticker()))
			{
				dto.setSticker(StickerFactory.createDTOFromSticker(player.getSticker()));
			}
			return dto;
		}
		return null;
	}

	public static Set<Player> createPlayersFromDTOs(List<PlayerDTO> dtos, Team team)
	{
		Set<Player> players = new HashSet<Player>();
		if (dtos != null)
		{
			for (PlayerDTO dto : dtos)
			{
				players.add(createPlayerFromDTO(dto, team));
			}
		}
		return players;
	}

	public static List<PlayerDTO> createDTOsFromPlayers(List<Player> players, TeamDTO teamDTO)
	{
		List<PlayerDTO> dtos = new ArrayList<PlayerDTO>();
		if (players != null)
		{
			for (Player player : players)
			{
				PlayerDTO dto = createDTOFromPlayer(player);
				dto.setTeam(teamDTO);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public static List<PlayerDTO> createDTOsFromPlayers(Set<Player> players)
	{
		List<PlayerDTO> dtos = new ArrayList<PlayerDTO>();
		if (players != null)
		{
			for (Player dto : players)
			{
				dtos.add(createDTOFromPlayer(dto));
			}
		}
		return dtos;
	}
}
