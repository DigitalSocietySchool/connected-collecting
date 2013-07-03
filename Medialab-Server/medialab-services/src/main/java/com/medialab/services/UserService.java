package com.medialab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.medialab.persistence.entity.User;
import com.medialab.services.generic.GenericService;

@Component
public class UserService extends GenericService<User, Long> {

	public UserService() {
		setClazz(User.class);
	}
	
	public User getUser(Long id)
	{
		String namedQuery = "User.getUser";
		List<Object> parameters = super.createParameterList(id);
		return super.getByNamedQuery(namedQuery, parameters);
	}

	public User getUserStickers(Long userId)
	{
		String namedQuery = "User.getUserStickers";
		List<Object> parameters = super.createParameterList(userId);
		return super.getByNamedQuery(namedQuery, parameters);
	}
	
	public Boolean hasDouble(Long userId, Long stickerId)
	{
		String namedQuery = "User.hasDouble";
		List<Object> parameters = super.createParameterList(userId, stickerId);
		return super.getBooleanByNamedQuery(namedQuery, parameters);
	}
	
	public User getUserStickerByTeam(User user, Long teamId)
	{
		String namedQuery = "User.getUserStickersByTeam";
		List<Object> parameters = super.createParameterList(user, teamId);
		return super.getByNamedQuery(namedQuery, parameters);
	}
	
	public List<User> getUserForTrade(Long userId, Long stickerId)
	{
		String namedQuery = "User.getUserForTrade";
		List<Object> parameters = super.createParameterList(userId, stickerId);
		return super.getListByNamedQuery(namedQuery, parameters);
	}
	
}
