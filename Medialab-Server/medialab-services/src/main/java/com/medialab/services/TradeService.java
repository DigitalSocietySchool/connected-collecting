package com.medialab.services;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.medialab.beans.TradeStatus;
import com.medialab.persistence.entity.Trade;
import com.medialab.services.generic.GenericService;

@Component
public class TradeService extends GenericService<Trade, Long>
{
	private static final Long TRADE_LIMIT = 5L;

	public TradeService()
	{
		super.setClazz(Trade.class);
	}

	public Boolean hasFiveActiveTrades(Long userId)
	{
		String namedQuery = "Trade.hasFiveActiveTrades";
		List<Object> parameters = super.createParameterList(TRADE_LIMIT, TradeStatus.WAITING, userId);
		return super.getBooleanByNamedQuery(namedQuery, parameters);
	}

	public Trade getTradeByUsers(Long initiatorId, Long respondentId, Long initiatorStickerId, Long respondentStickerId)
	{
		String namedQuery = "Trade.getTradeByUsers";
		List<Object> parameters = super.createParameterList(TradeStatus.WAITING, initiatorId, respondentId, initiatorStickerId,
				respondentStickerId);
		return super.getByNamedQuery(namedQuery, parameters);
	}

	@Transactional
	public int updateTrade(Long tradeId, TradeStatus status)
	{
		String namedQuery = "Trade.updateTrade";
		List<Object> parameters = super.createParameterList(status, tradeId);
		return super.executeUpdate(namedQuery, parameters);
	}

	public List<Trade> getInitiatorTrade(Long userId, TradeStatus status, int limit)
	{
		String namedQuery = "Trade.getInitiatorTrade";
		return getTrades(namedQuery, userId, status, limit);
	}

	public List<Trade> getRespondentTrade(Long userId, TradeStatus status, int limit)
	{
		String namedQuery = "Trade.getRespondentTrade";
		return getTrades(namedQuery, userId, status, limit);
	}

	public List<Trade> getLastTrades(Long userId, TradeStatus status, int limit)
	{
		String namedQuery = "Trade.getLastTradesByStatus";
		List<Object> parameters = super.createParameterList(userId, userId, status);
		return (limit == 0 ? super.getListByNamedQuery(namedQuery, parameters) : super.getListByNamedQuery(namedQuery, parameters, limit));
	}
	
	@Transactional
	public void clear()
	{
		String namedQuery = "Trade.clear";
		super.executeQueryUpdate(namedQuery);
	}
	
	public List<Trade> getLastTrades(Long userId, int limit)
	{
		String namedQuery = "Trade.getLastTrades";
		List<Object> parameters = super.createParameterList(userId, userId);
		return (limit == 0 ? super.getListByNamedQuery(namedQuery, parameters) : super.getListByNamedQuery(namedQuery, parameters, limit));
	}	

	private List<Trade> getTrades(String namedQuery, Long userId, TradeStatus status, int limit)
	{
		List<Object> parameters = super.createParameterList(userId, status);
		return (limit == 0 ? super.getListByNamedQuery(namedQuery, parameters) : super.getListByNamedQuery(namedQuery, parameters, limit));
	}

}
