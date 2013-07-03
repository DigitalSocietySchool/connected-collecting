package com.medialab.factory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import com.medialab.dto.TeamDTO;
import com.medialab.persistence.entity.Team;

public class TeamFactory
{

	private TeamFactory()
	{

	}

	public static Team createTeamFromDTO(TeamDTO dto)
	{
		if (dto != null)
		{
			Team team = new Team();
			team.setId(dto.getId());
			team.setDescription(dto.getDescription());
			team.setColor(dto.getColor());
			team.setName(dto.getName());
			team.setAlbum(AlbumFactory.createAlbumFromDTO(dto.getAlbum()));
			team.setPlayers(PlayerFactory.createPlayersFromDTOs(dto.getPlayers(), team));
			return team;
		}
		return null;
	}

	public static TeamDTO createDTOFromTeam(Team team)
	{
		if (team != null)
		{
			TeamDTO dto = new TeamDTO();
			dto.setId(team.getId());
			dto.setDescription(team.getDescription());
			dto.setColor(team.getColor());
			dto.setName(team.getName());
			if (Hibernate.isInitialized(team.getAlbum()))
			{
				dto.setAlbum(AlbumFactory.createDTOFromAlbum(team.getAlbum()));
			}
			if (Hibernate.isInitialized(team.getPlayers()))
			{
				dto.setPlayers(PlayerFactory.createDTOsFromPlayers(team.getPlayers()));
			}
			if (Hibernate.isInitialized(team.getStickers()))
			{
				dto.setStickerCount(team.getStickers().size());
			}
			return dto;
		}
		return null;
	}

	public static List<Team> createTeamsFromDTOs(List<TeamDTO> dtos)
	{
		List<Team> teams = new ArrayList<Team>();
		if (dtos != null)
		{
			for (TeamDTO dto : dtos)
			{
				teams.add(createTeamFromDTO(dto));
			}
		}
		return teams;
	}

	public static List<TeamDTO> createDTOsFromTeams(List<Team> teams)
	{
		List<TeamDTO> dtos = new ArrayList<TeamDTO>();
		if (teams != null)
		{
			for (Team dto : teams)
			{
				dtos.add(createDTOFromTeam(dto));
			}
		}
		return dtos;
	}
}
