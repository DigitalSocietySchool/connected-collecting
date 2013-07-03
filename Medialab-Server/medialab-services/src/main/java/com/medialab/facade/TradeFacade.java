package com.medialab.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.TradeResult;
import com.medialab.beans.TradeStatus;
import com.medialab.dto.TradeDTO;
import com.medialab.factory.TradeFactory;
import com.medialab.persistence.entity.Sticker;
import com.medialab.persistence.entity.Trade;
import com.medialab.persistence.entity.User;
import com.medialab.services.StickerService;
import com.medialab.services.TradeService;
import com.medialab.services.UserService;

@Component
public class TradeFacade
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeFacade.class);

	@Autowired
	private TradeService service;

	@Autowired
	private UserService userService;

	@Autowired
	private StickerService stickerService;

	public TradeResult getLastTrades(Long userId, TradeStatus status, int limit)
	{
		try
		{
			List<Trade> trades = service.getLastTrades(userId, status, limit);
			return createResult(trades);
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving last trades.", ex);
			return new TradeResult(MediaLABResponse.UNKOWN_ERROR);
		}
	}

	public void clear()
	{
		try
		{
			service.clear();
		} catch (Exception ex)
		{
			LOGGER.error("Error clearing trades.", ex);
		}
	}
	
	public TradeResult getLastTrades(Long userId, int limit)
	{
		try
		{
			List<Trade> trades = service.getLastTrades(userId, limit);
			return createResult(trades);
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving last trades.", ex);
			return new TradeResult(MediaLABResponse.UNKOWN_ERROR);
		}
	}

	public TradeResult getInitiatorTrade(Long userId, TradeStatus status, int limit)
	{
		try
		{
			List<Trade> trades = service.getInitiatorTrade(userId, status, limit);
			return createResult(trades);
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving initiator trades.", ex);
			return new TradeResult(MediaLABResponse.UNKOWN_ERROR);
		}
	}

	public TradeResult getRespondentTrade(Long userId, TradeStatus status, int limit)
	{
		try
		{
			List<Trade> trades = service.getRespondentTrade(userId, status, limit);
			return createResult(trades);
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving respondent trades.", ex);
			return new TradeResult(MediaLABResponse.UNKOWN_ERROR);
		}
	}

	/**
	 * 
	 * @param trades
	 * @return
	 */
	private TradeResult createResult(List<Trade> trades)
	{
		TradeResult result = new TradeResult();
		result.setResponse(MediaLABResponse.USER_NOT_FOUND);
		try
		{
			if (trades != null)
			{
				List<TradeDTO> dtos = TradeFactory.createDTOsFromTrades(trades);

				result.setResponse(MediaLABResponse.SUCCESS);
				result.setTrades(dtos);
			}
		} catch (Exception e)
		{
			LOGGER.error("Error creating trade result.", e);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}

	public TradeResult updateTrade(Long tradeId, TradeStatus status)
	{
		TradeResult result = new TradeResult();
		result.setResponse(MediaLABResponse.TRADE_NOT_FOUND);
		try
		{
			Trade trade = service.findOne(tradeId);
			if (trade != null)
			{
				if (!TradeStatus.WAITING.equals(trade.getStatus()))
				{
					result.setResponse(MediaLABResponse.TRADE_ALREADY_CLOSED);
					return result;
				}
				
				User initiator = trade.getInitiator(), respondent = trade.getRespondent();
				Sticker initiatorSticker = trade.getInitiatorSticker(), respondentSticker = trade.getRespondentSticker();
				boolean initiatorHasDouble = userService.hasDouble(initiator.getId(), initiatorSticker.getId());
				boolean respondentHasDouble = userService.hasDouble(respondent.getId(), respondentSticker.getId());
				if (!initiatorHasDouble)
				{
					result.setResponse(MediaLABResponse.INITIATOR_DOUBLE_NOT_FOUND);
					return result;
				} else if (!respondentHasDouble)
				{
					result.setResponse(MediaLABResponse.RESPONDENT_DOUBLE_NOT_FOUND);
					return result;
				}
				if (TradeStatus.ACCEPTED.equals(status))
				{
					// updating initiator stickers, adding the new one and
					// removing the old one
					User iUser = userService.getUserStickers(initiator.getId());
					iUser.getStickers().add(respondentSticker);
					iUser.getStickers().remove(initiatorSticker);
					userService.merge(iUser);

					User rUser = userService.getUserStickers(respondent.getId());
					rUser.getStickers().add(initiatorSticker);
					rUser.getStickers().remove(respondentSticker);
					userService.merge(rUser);
				}
				service.updateTrade(tradeId, status);
				result.setResponse(MediaLABResponse.SUCCESS);
			}
		} catch (Exception ex)
		{
			LOGGER.error("Error updating trade.", ex);
			return new TradeResult(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}

	public TradeResult createTrade(Long initiatorId, Long respondentId, Long initiatorStickerId, Long respondentStickerId)
	{
		TradeResult result = new TradeResult();
		result.setResponse(MediaLABResponse.USER_NOT_FOUND);
		try
		{
			User initiator = userService.getUser(initiatorId);
			User respondent = userService.getUser(respondentId);
			if (initiator != null && respondent != null && initiator.getId() != null && respondent.getId() != null)
			{
				Trade _trade = service.getTradeByUsers(initiatorId, respondentId, initiatorStickerId, respondentStickerId);
				Trade __trade = service.getTradeByUsers(respondentId, initiatorId, respondentStickerId, initiatorStickerId);
				if ((_trade != null && TradeStatus.WAITING.equals(_trade.getStatus())) || (__trade != null && TradeStatus.WAITING.equals(__trade.getStatus())))
				{
					result.setResponse(MediaLABResponse.TRADE_ALREADY_CREATED);
					return result;
				}
				boolean hasFiveTrades = service.hasFiveActiveTrades(initiatorId);
				if (hasFiveTrades)
				{
					result.setResponse(MediaLABResponse.TRADE_LIMIT);
					return result;
				}
				boolean initiatorHasDouble = userService.hasDouble(initiatorId, initiatorStickerId);
				boolean respondentHasDouble = userService.hasDouble(respondentId, respondentStickerId);
				if (!initiatorHasDouble)
				{
					result.setResponse(MediaLABResponse.INITIATOR_DOUBLE_NOT_FOUND);
					return result;
				} else if (!respondentHasDouble)
				{
					result.setResponse(MediaLABResponse.RESPONDENT_DOUBLE_NOT_FOUND);
					return result;
				}

				result.setResponse(MediaLABResponse.SUCCESS);
				Sticker initiatorSticker = stickerService.findOne(initiatorStickerId);
				Sticker respondentSticker = stickerService.findOne(respondentStickerId);

				Trade trade = new Trade();
				trade.setInitiator(initiator);
				trade.setRespondent(respondent);
				trade.setStartDate(new Date());
				trade.setStatus(TradeStatus.WAITING);
				trade.setInitiatorSticker(initiatorSticker);
				trade.setRespondentSticker(respondentSticker);
				service.save(trade);

				result.setTrades(createTrades(TradeFactory.createDTOFromTrade(trade)));
			}
		} catch (Exception e)
		{
			LOGGER.error("Error creating trade.", e);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private List<TradeDTO> createTrades(TradeDTO dto)
	{
		List<TradeDTO> dtos = new ArrayList<TradeDTO>();
		dtos.add(dto);
		return dtos;
	}
}
