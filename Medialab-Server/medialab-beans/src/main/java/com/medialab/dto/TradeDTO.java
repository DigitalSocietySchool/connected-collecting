package com.medialab.dto;

import java.util.Date;

import com.medialab.beans.TradeStatus;

public class TradeDTO
{
	private Long id;

	private Date startDate;

	private UserDTO initiator;

	private UserDTO respondent;

	private StickerDTO initiatorSticker;

	private StickerDTO respondentSticker;

	private TradeStatus status;

	public TradeDTO()
	{
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return the initiator
	 */
	public UserDTO getInitiator()
	{
		return initiator;
	}

	/**
	 * @param initiator
	 *            the initiator to set
	 */
	public void setInitiator(UserDTO initiator)
	{
		this.initiator = initiator;
	}

	/**
	 * @return the respondent
	 */
	public UserDTO getRespondent()
	{
		return respondent;
	}

	/**
	 * @param respondent
	 *            the respondent to set
	 */
	public void setRespondent(UserDTO respondent)
	{
		this.respondent = respondent;
	}

	/**
	 * @return the initiatorSticker
	 */
	public StickerDTO getInitiatorSticker()
	{
		return initiatorSticker;
	}

	/**
	 * @param initiatorSticker
	 *            the initiatorSticker to set
	 */
	public void setInitiatorSticker(StickerDTO initiatorSticker)
	{
		this.initiatorSticker = initiatorSticker;
	}

	/**
	 * @return the respondentSticker
	 */
	public StickerDTO getRespondentSticker()
	{
		return respondentSticker;
	}

	/**
	 * @param respondentSticker
	 *            the respondentSticker to set
	 */
	public void setRespondentSticker(StickerDTO respondentSticker)
	{
		this.respondentSticker = respondentSticker;
	}

	/**
	 * @return the status
	 */
	public TradeStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(TradeStatus status)
	{
		this.status = status;
	}

}
