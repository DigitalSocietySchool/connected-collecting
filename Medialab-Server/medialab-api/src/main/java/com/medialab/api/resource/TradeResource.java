package com.medialab.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.ConnectorConstants;
import com.medialab.beans.TradeResult;
import com.medialab.beans.TradeStatus;
import com.medialab.facade.TradeFacade;

@Component
@Path(ConnectorConstants.TRADE_RESOURCE_NAME)
@Produces({ MediaType.APPLICATION_JSON })
public class TradeResource
{
	@Autowired
	private TradeFacade facade;

	@GET
	@Path(ConnectorConstants.GET_LAST_TRADES_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{"
			+ ConnectorConstants.TRADE_STATUS_NAME + "}" + "/{" + ConnectorConstants.LIMIT_NAME + "}")
	public TradeResult getLastTrades(@PathParam(ConnectorConstants.USER_ID_NAME) Long userId,
			@PathParam(ConnectorConstants.TRADE_STATUS_NAME) TradeStatus status, 
			@PathParam(ConnectorConstants.LIMIT_NAME) int limit)
	{
		return facade.getLastTrades(userId, status, limit);
	}
	
	@GET
	@Path(ConnectorConstants.GET_LAST_TRADES_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{" + ConnectorConstants.LIMIT_NAME + "}")
	public TradeResult getLastTrades(@PathParam(ConnectorConstants.USER_ID_NAME) Long userId, 
			@PathParam(ConnectorConstants.LIMIT_NAME) int limit)
	{
		return facade.getLastTrades(userId, limit);
	}
	
	@GET
	@Path(ConnectorConstants.GET_INITIATOR_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{"
			+ ConnectorConstants.TRADE_STATUS_NAME + "}" + "/{" + ConnectorConstants.LIMIT_NAME + "}")
	public TradeResult getInitiatorTrade(@PathParam(ConnectorConstants.USER_ID_NAME) Long userId,
			@PathParam(ConnectorConstants.TRADE_STATUS_NAME) TradeStatus status, 
			@PathParam(ConnectorConstants.LIMIT_NAME) int limit)
	{
		return facade.getInitiatorTrade(userId, status, limit);
	}
	
	@GET
	@Path(ConnectorConstants.GET_RESPONDENT_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{"
			+ ConnectorConstants.TRADE_STATUS_NAME + "}" + "/{" + ConnectorConstants.LIMIT_NAME + "}")
	public TradeResult getRespondentTrade(@PathParam(ConnectorConstants.USER_ID_NAME) Long userId,
			@PathParam(ConnectorConstants.TRADE_STATUS_NAME) TradeStatus status, 
			@PathParam(ConnectorConstants.LIMIT_NAME) int limit)
	{
		return facade.getRespondentTrade(userId, status, limit);
	}
	
	@PUT
	@Path(ConnectorConstants.UPDATE_TRADE_NAME + "/{" + ConnectorConstants.TRADE_ID_NAME + "}" + "/{" + ConnectorConstants.TRADE_STATUS_NAME + "}")
	public TradeResult updateTrade(@PathParam(ConnectorConstants.TRADE_ID_NAME) Long tradeId,
			@PathParam(ConnectorConstants.TRADE_STATUS_NAME) TradeStatus status)
	{
		return facade.updateTrade(tradeId, status);
	}

	@POST
	@Path(ConnectorConstants.CREATE_TRADE_NAME + "/{" + ConnectorConstants.USER_ID_NAME + "}" + "/{"
			+ ConnectorConstants.ANOTHER_USER_ID_NAME + "}" + "/{" + ConnectorConstants.INITIATOR_STICKER_ID_NAME + "}" + "/{"
			+ ConnectorConstants.RESPONDENT_STICKER_ID_NAME + "}")
	public TradeResult createTrade(@PathParam(ConnectorConstants.USER_ID_NAME) Long initiatorId,
			@PathParam(ConnectorConstants.ANOTHER_USER_ID_NAME) Long respondentId,
			@PathParam(ConnectorConstants.INITIATOR_STICKER_ID_NAME) Long initiatorStickerId,
			@PathParam(ConnectorConstants.RESPONDENT_STICKER_ID_NAME) Long respondentStickerId)
	{
		return facade.createTrade(initiatorId, respondentId, initiatorStickerId, respondentStickerId);
	}

}
