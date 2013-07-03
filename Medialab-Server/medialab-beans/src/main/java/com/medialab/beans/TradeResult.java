package com.medialab.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.dto.TradeDTO;

@XmlRootElement
public class TradeResult
{
	private MediaLABResponse response;

	private List<TradeDTO> trades;

	public TradeResult()
	{
		// TODO Auto-generated constructor stub
	}

	public TradeResult(MediaLABResponse response)
	{
		this.response = response;
	}

	/**
	 * @return the response
	 */
	public MediaLABResponse getResponse()
	{
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(MediaLABResponse response)
	{
		this.response = response;
	}

	/**
	 * @return the trades
	 */
	public List<TradeDTO> getTrades()
	{
		return trades;
	}

	/**
	 * @param trades
	 *            the trades to set
	 */
	public void setTrades(List<TradeDTO> trades)
	{
		this.trades = trades;
	}

}
