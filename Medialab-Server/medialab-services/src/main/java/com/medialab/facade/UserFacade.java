package com.medialab.facade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kooaba.client.KooabaJAXRSClient;
import com.kooaba.client.model.QueryResult;
import com.kooaba.client.model.ResultRecord;
import com.kooaba.client.model.StickerData;
import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.UserResult;
import com.medialab.dto.UserDTO;
import com.medialab.factory.UserFactory;
import com.medialab.persistence.entity.Sticker;
import com.medialab.persistence.entity.User;
import com.medialab.services.RankService;
import com.medialab.services.StickerService;
import com.medialab.services.TradeService;
import com.medialab.services.UserService;
import com.medialab.util.FileUtil;

@Component
public class UserFacade
{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

	private static final String SECRET_TOKEN = "cfaUCfrhUd17zytFfIMq050mrdz5w75Yo28ESRag";

	@Autowired
	private UserService userService;

	@Autowired
	private StickerService stickerService;

	@Autowired
	private RankService rankService;
	
	@Autowired
	private TradeService tradeService;

	private List<UserDTO> createUsers(UserDTO dto)
	{
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		dtos.add(dto);
		return dtos;
	}

	public MediaLABResponse cleanDB()
	{
		try
		{
			User user = userService.getUserStickers(1L), _user = userService.getUserStickers(2L);
			
			Sticker s47 = stickerService.findOne(47L), s49 = stickerService.findOne(49L), s55 = stickerService.findOne(55L), s10 = stickerService.findOne(10L);
			Sticker s63 = stickerService.findOne(63L), s64 = stickerService.findOne(64L), s65 = stickerService.findOne(65L), s7 = stickerService.findOne(7L);
			user.getStickers().clear();
			user.getStickers().add(s47);
			user.getStickers().add(s47);
			user.getStickers().add(s49);
			user.getStickers().add(s49);
			user.getStickers().add(s55);
			user.getStickers().add(s55);
			user.getStickers().add(s10);
			userService.merge(user);
			_user.getStickers().clear();
			_user.getStickers().add(s63);
			_user.getStickers().add(s63);
			_user.getStickers().add(s64);
			_user.getStickers().add(s64);
			_user.getStickers().add(s65);
			_user.getStickers().add(s65);
			_user.getStickers().add(s7);
			userService.merge(_user);
			tradeService.clear();
			return MediaLABResponse.SUCCESS;
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving user.", ex);
			return MediaLABResponse.UNKOWN_ERROR;
		}
	}

	public UserResult getById(Long id)
	{
		UserResult result = new UserResult();
		result.setResponse(MediaLABResponse.USER_NOT_FOUND);
		try
		{
			User user = userService.getUser(id);
			if (user != null && user.getId() != null)
			{
				result.setResponse(MediaLABResponse.SUCCESS);
				int nextLevelCount = rankService.calculateStickersNeeded(user.getRank(), user.getStickerCount().intValue());
				UserDTO dto = UserFactory.createDTOFromUser(user, nextLevelCount, user.getStickerCount().intValue());
				result.setUsers(createUsers(dto));
			}
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving user.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}

	public UserResult getUserForTrade(Long userId, Long stickerId)
	{
		UserResult result = new UserResult();
		result.setResponse(MediaLABResponse.USER_NOT_FOUND);
		try
		{
			List<User> users = userService.getUserForTrade(userId, stickerId);
			if (users != null)
			{
				result.setResponse(MediaLABResponse.SUCCESS);
				List<UserDTO> dtos = UserFactory.createDTOsFromUsers(users);
				result.setUsers(dtos);
			}
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving users for trade", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}

	public UserResult getUserStickers(Long userId, Long teamId)
	{
		UserResult result = new UserResult();
		result.setResponse(MediaLABResponse.USER_NOT_FOUND);
		try
		{
			User user = userService.getUser(userId);
			if (user != null && user.getId() != null)
			{
				result.setResponse(MediaLABResponse.NO_STICKERS_FOUND);
				int nextLevelCount = rankService.calculateStickersNeeded(user.getRank(), user.getStickerCount().intValue());
				int userStickerCount = user.getStickerCount().intValue();
				User _user = userService.getUserStickerByTeam(user, teamId);
				if (_user != null)
				{
					user.setStickers(_user.getStickers());
					result.setResponse(MediaLABResponse.STICKERS_RETRIEVE_SUCESS);
				} else
				{
					user.setStickers(new ArrayList<Sticker>());
				}
				UserDTO dto = UserFactory.createDTOFromUser(user, nextLevelCount, userStickerCount);
				result.setUsers(createUsers(dto));
			}
		} catch (Exception ex)
		{
			LOGGER.error("Error retrieving user stickers.", ex);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}

	// TODO move the filters
	public UserResult addSticker(InputStream is, Long userId)
	{
		UserResult result = new UserResult();
		try
		{
			LOGGER.debug("Adding user Sticker");
			User user = userService.getUserStickers(userId);

			if (user == null)
			{
				result.setResponse(MediaLABResponse.USER_NOT_FOUND);
				return result;
			}

			File file = FileUtil.inputStreamToFile(is);
			// temp solution
			MatchResponse matchResponse = getMatchSticker(file);
			String stickerNumber = matchResponse.getNumber();

			if (stickerNumber == null)
			{
				LOGGER.info("No match founds.");
				result.setResponse(MediaLABResponse.NO_MATCHES_FOUND);
				return result;
			}

			LOGGER.debug("Found a match: " + stickerNumber);
			result.setStickerName(matchResponse.getName());
			Sticker sticker = stickerService.getByNumber(stickerNumber);
			// gone now we accept repeated stickers
			// if (isStickerRepeated(user.getStickers(), sticker))
			// {
			// result.setResponse(MediaLABResponse.ALREADY_HAVE_STICKER);
			// return result;
			// }

			user.getStickers().add(sticker);

			userService.merge(user);
			int nextLevelCount = rankService.calculateStickersNeeded(user.getRank(), user.getStickers().size());
			result.setUsers(createUsers(UserFactory.createDTOFromUser(user, nextLevelCount, user.getStickerCount().intValue())));
			result.setResponse(MediaLABResponse.STICKER_ADDED);
			return result;
		} catch (Exception e)
		{
			LOGGER.error("Error adding sticker", e);
			result.setResponse(MediaLABResponse.UNKOWN_ERROR);
		}
		return result;
	}

	// private boolean isStickerRepeated(List<Sticker> userStickers, Sticker
	// scannedSticker)
	// {
	// for (Sticker sticker : userStickers)
	// {
	// if (sticker.equals(scannedSticker))
	// {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 
	 * @param file
	 * @return the number of the matching sticker
	 * @throws FileNotFoundException
	 */
	private MatchResponse getMatchSticker(File file) throws FileNotFoundException
	{
		MatchResponse response = new MatchResponse();
		KooabaJAXRSClient client = new KooabaJAXRSClient(SECRET_TOKEN);
		QueryResult result = client.compareImage(file);
		if (result != null && result.getResults() != null)
		{
			ResultRecord record = result.getBestMatch();
			StickerData data = record.getMetadata();
			Float score = record.getScore();
			LOGGER.info("Sticker info: " + data + ", Score: " + score);
			// Only consider a match if score is higher or equal to 6
			response.setName(data.getName());
			response.setNumber((score.compareTo(new Float(6)) >= 0 ? data.getNumber() : null));
		}
		return response;
	}

}
