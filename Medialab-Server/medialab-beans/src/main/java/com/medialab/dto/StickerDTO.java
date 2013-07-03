package com.medialab.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.medialab.beans.StickerType;

@XmlRootElement
public class StickerDTO
{
	private Long id;

	private String number;

	private String image;

	private StickerType type;

	private List<ContentDTO> content;

	public StickerDTO()
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
	 * @return the number
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return the image
	 */
	public String getImage()
	{
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image)
	{
		this.image = image;
	}

	/**
	 * @return the type
	 */
	public StickerType getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(StickerType type)
	{
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public List<ContentDTO> getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(List<ContentDTO> content)
	{
		this.content = content;
	}

}
