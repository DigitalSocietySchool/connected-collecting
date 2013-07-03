package com.medialab.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VideoDTO
{
	private Long id;

	private String url;

	public VideoDTO()
	{
	}

	public VideoDTO(Long id, String url)
	{
		this.id = id;
		this.url = url;
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
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

}
