package com.medialab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.medialab.persistence.entity.Sticker;
import com.medialab.services.generic.GenericService;

@Component
public class StickerService extends GenericService<Sticker, Long> {

	public StickerService() {
		super.setClazz(Sticker.class);
	}
	
	public Sticker getByNumber(String number)
	{
		String namedQuery = "Sticker.getByNumber";
		List<Object> parameters = super.createParameterList(number);
		return super.getByNamedQuery(namedQuery, parameters);
	}
	
	public List<Sticker> getDoublesForAnotherUser(Long userId, Long anotherUser)
	{
		String namedQuery = "Sticker.getDoublesForAnotherUser";
		List<Object> parameters = super.createParameterList(userId, anotherUser);
		return super.getListByNamedQuery(namedQuery, parameters);
	}
	
}
