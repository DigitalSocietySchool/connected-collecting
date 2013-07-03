package com.medialab.factory;

import java.util.ArrayList;
import java.util.List;

import com.medialab.dto.TradeDTO;
import com.medialab.persistence.entity.Trade;

public class TradeFactory
{

	private TradeFactory()
	{
	}
	
	public static Trade createTradeFromDTO(TradeDTO dto)
	{
		if (dto != null)
		{
			Trade trade = new Trade();
			trade.setId(dto.getId());
			trade.setStartDate(dto.getStartDate());
			trade.setStatus(dto.getStatus());
			trade.setInitiator(UserFactory.createUserFromDTO(dto.getInitiator()));
			trade.setRespondent(UserFactory.createUserFromDTO(dto.getRespondent()));
			trade.setInitiatorSticker(StickerFactory.createStickerFromDTO(dto.getInitiatorSticker()));
			trade.setRespondentSticker(StickerFactory.createStickerFromDTO(dto.getRespondentSticker()));
			return trade;
		}
		return null;
	}
	
	public static TradeDTO createDTOFromTrade(Trade trade)
	{
		if (trade != null)
		{
			TradeDTO dto = new TradeDTO();
			dto.setId(trade.getId());
			dto.setStartDate(trade.getStartDate());
			dto.setStatus(trade.getStatus());
			dto.setInitiator(UserFactory.createDTOFromUser(trade.getInitiator(), 0, 0));
			dto.setRespondent(UserFactory.createDTOFromUser(trade.getRespondent(), 0, 0));
			dto.setInitiatorSticker(StickerFactory.createDTOFromSticker(trade.getInitiatorSticker()));
			dto.setRespondentSticker(StickerFactory.createDTOFromSticker(trade.getRespondentSticker()));
			return dto;
		}
		return null;
	}
	
	public static List<Trade> createTradesFromDTOs(List<TradeDTO> dtos)
	{
		List<Trade> teams = new ArrayList<Trade>();
		if (dtos != null)
		{
			for (TradeDTO dto : dtos)
			{
				teams.add(createTradeFromDTO(dto));
			}
		}
		return teams;
	}

	public static List<TradeDTO> createDTOsFromTrades(List<Trade> teams)
	{
		List<TradeDTO> dtos = new ArrayList<TradeDTO>();
		if (teams != null)
		{
			for (Trade dto : teams)
			{
				dtos.add(createDTOFromTrade(dto));
			}
		}
		return dtos;
	}
}
