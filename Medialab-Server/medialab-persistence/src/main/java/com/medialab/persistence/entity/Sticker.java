package com.medialab.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CacheModeType;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.medialab.beans.StickerType;

@Entity
@Table(name = "STICKER")
@NamedQueries({
	@NamedQuery(name = "Sticker.getByNumber", query = "SELECT s FROM Sticker s WHERE s.number = ?", cacheable = true, cacheMode = CacheModeType.NORMAL, cacheRegion = "com.medialab.persistence.entity.Sticker"),
	@NamedQuery(name = "Sticker.getDoublesForAnotherUser", query = "SELECT new Sticker(ss, count(ss.id)) FROM User us INNER JOIN us.stickers ss WHERE us.id = ? AND ss NOT IN (SELECT s FROM User u INNER JOIN u.stickers s WHERE u.id = ?) GROUP BY ss.id HAVING COUNT(ss.id) > 1", cacheable = false)
})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.medialab.persistence.entity.Sticker")
public class Sticker implements Serializable {

	private static final long serialVersionUID = 5074508087731862472L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NUMBER")
	private String number;
	
	@Column(name = "IMAGE")
	private String image;

	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private StickerType type;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.medialab.persistence.entity.Content")
	private List<Content> content;

	public Sticker() {
	}
	
	public Sticker(Sticker s, Long count)
	{
		this.id = s.id;
		this.number = s.number;
		this.image = s.image;
		this.type = s.type;
		this.content = s.content;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
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
	 * @param image the image to set
	 */
	public void setImage(String image)
	{
		this.image = image;
	}

	/**
	 * @return the type
	 */
	public StickerType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(StickerType type) {
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public List<Content> getContent()
	{
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(List<Content> content)
	{
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sticker other = (Sticker) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
	
}
