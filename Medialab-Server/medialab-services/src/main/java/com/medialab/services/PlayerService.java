package com.medialab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.medialab.persistence.entity.Player;
import com.medialab.services.generic.GenericService;

@Component
public class PlayerService extends GenericService<Player, Long> {

	public PlayerService() {
		super.setClazz(Player.class);
	}
	
	public Player getByName(String name)
	{
		String namedQuery = "Player.getByName";
		List<Object> parameters = super.createParameterList(name);
		return super.getByNamedQuery(namedQuery, parameters);
	}
	
	public Player getByStickerId(Long stickerId)
	{
		String namedQuery = "Player.getByStickerId";
		List<Object> parameters = super.createParameterList(stickerId);
		return super.getByNamedQuery(namedQuery, parameters);
	}
	
	public Player getByStickerNumber(String number)
	{
		String namedQuery = "Player.getByStickerNumber";
		List<Object> parameters = super.createParameterList(number);
		return super.getByNamedQuery(namedQuery, parameters);
	}
	
	public List<Player> getByTeamId(Long teamId)
	{
		String namedQuery = "Player.getByTeamId";
		List<Object> parameters = super.createParameterList(teamId);
		return super.getListByNamedQuery(namedQuery, parameters);
	}

}
