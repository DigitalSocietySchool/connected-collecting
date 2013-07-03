package com.medialab.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.dto.StickerDTO;

@XmlRootElement
public class StickerResult
{
	private MediaLABResponse response;

	private List<StickerDTO> stickers;

	public StickerResult()
	{
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
	 * @return the stickers
	 */
	public List<StickerDTO> getStickers()
	{
		return stickers;
	}

	/**
	 * @param stickers
	 *            the stickers to set
	 */
	public void setStickers(List<StickerDTO> stickers)
	{
		this.stickers = stickers;
	}

}
