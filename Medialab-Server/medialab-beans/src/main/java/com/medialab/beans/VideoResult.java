package com.medialab.beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.dto.VideoDTO;

@XmlRootElement
public class VideoResult
{
	private MediaLABResponse response;

	private VideoDTO dto;

	public VideoResult()
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
	 * @return the dto
	 */
	public VideoDTO getDto()
	{
		return dto;
	}

	/**
	 * @param dto
	 *            the dto to set
	 */
	public void setDto(VideoDTO dto)
	{
		this.dto = dto;
	}

}
