package com.medialab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.medialab.persistence.entity.Team;
import com.medialab.services.generic.GenericService;

@Component
public class TeamService extends GenericService<Team, Long> {
	
	private static final Long ALBUM_ID = 1L;

	public TeamService() {
		super.setClazz(Team.class);
	}
	
	public List<Team> getTeams()
	{
		String namedQuery = "Team.getByAlbumId";
		List<Object> parameters = super.createParameterList(ALBUM_ID);
		return super.getListByNamedQuery(namedQuery, parameters);
	}
	
	public Team getTeam(Long id)
	{
		String namedQuery = "Team.getById";
		List<Object> parameters = super.createParameterList(ALBUM_ID, id);
		return super.getByNamedQuery(namedQuery, parameters);
	}

}
