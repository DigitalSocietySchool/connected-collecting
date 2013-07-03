package com.medialab.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.medialab.beans.TradeStatus;

@Entity
@Table(name = "TRADE")
@NamedQueries({
	@NamedQuery(name = "Trade.getInitiatorTrade", query = "SELECT t FROM Trade t WHERE t.initiator.id = ? AND t.status = ? ORDER BY t.startDate"),
	@NamedQuery(name = "Trade.getRespondentTrade", query = "SELECT t FROM Trade t WHERE t.respondent.id = ? AND t.status = ? ORDER BY t.startDate"),
	@NamedQuery(name = "Trade.getLastTradesByStatus", query = "SELECT t FROM Trade t WHERE (t.initiator.id = ?  OR t.respondent.id = ?) AND t.status = ? ORDER BY t.startDate"),
	@NamedQuery(name = "Trade.getLastTrades", query = "SELECT t FROM Trade t WHERE (t.initiator.id = ?  OR t.respondent.id = ?) ORDER BY t.startDate"),
	
	@NamedQuery(name = "Trade.clear", query = "DELETE FROM Trade"),
	@NamedQuery(name = "Trade.updateTrade", query = "UPDATE Trade t SET status = ? WHERE t.id = ?"),
	@NamedQuery(name = "Trade.getTradeByUsers", query = "SELECT t FROM Trade t WHERE t.status = ? AND t.initiator.id = ? AND t.respondent.id = ? AND t.initiatorSticker.id = ? AND t.respondentSticker.id = ?"),
	@NamedQuery(name = "Trade.hasFiveActiveTrades", query = "SELECT case when (count(t) >= ?) then true else false end FROM Trade t WHERE t.status = ? AND t.initiator.id = ?")
})
public class Trade implements Serializable
{
	private static final long serialVersionUID = 8823655246740830473L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "START_DATE")
	private Date startDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INITIATOR_ID")
	private User initiator;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RESPONDENT_ID")
	private User respondent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INITIATOR_STICKER_ID")
	private Sticker initiatorSticker;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RESPONDENT_STICKER_ID")
	private Sticker respondentSticker;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private TradeStatus status;

	public Trade()
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
	public User getInitiator()
	{
		return initiator;
	}

	/**
	 * @param initiator
	 *            the initiator to set
	 */
	public void setInitiator(User initiator)
	{
		this.initiator = initiator;
	}

	/**
	 * @return the respondent
	 */
	public User getRespondent()
	{
		return respondent;
	}

	/**
	 * @param respondent
	 *            the respondent to set
	 */
	public void setRespondent(User respondent)
	{
		this.respondent = respondent;
	}

	/**
	 * @return the initiatorSticker
	 */
	public Sticker getInitiatorSticker()
	{
		return initiatorSticker;
	}

	/**
	 * @param initiatorSticker
	 *            the initiatorSticker to set
	 */
	public void setInitiatorSticker(Sticker initiatorSticker)
	{
		this.initiatorSticker = initiatorSticker;
	}

	/**
	 * @return the respondentSticker
	 */
	public Sticker getRespondentSticker()
	{
		return respondentSticker;
	}

	/**
	 * @param respondentSticker
	 *            the respondentSticker to set
	 */
	public void setRespondentSticker(Sticker respondentSticker)
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
