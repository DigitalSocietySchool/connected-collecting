package com.medialab.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "USER")
@NamedQueries({
		@NamedQuery(name = "User.getUser", query = "SELECT new User(u, count(distinct s.id)) FROM User u LEFT JOIN u.stickers s WHERE u.id = ?"),
		@NamedQuery(name = "User.getUserStickers", query = "SELECT new User(u, count(distinct s.id)) FROM User u LEFT JOIN u.stickers s WHERE u.id = ?"),
		@NamedQuery(name = "User.hasDouble", query = "SELECT case when (count(s) > 1) then true else false end FROM User u INNER JOIN u.stickers s WHERE u.id = ? AND s.id = ?"),
		@NamedQuery(name = "User.getUserStickersByTeam", query = "SELECT u FROM User u JOIN FETCH u.stickers s WHERE u = ? AND EXISTS (SELECT t.id FROM Team t INNER JOIN t.stickers ts WHERE ts.id = s.id AND t.id = ?)"),
		@NamedQuery(name = "User.getUserForTrade", query = "SELECT new User(u, count(s.id))	FROM User u INNER JOIN u.stickers s WHERE NOT EXISTS (SELECT new User(us, count(ss.id)) FROM User us INNER JOIN us.stickers ss WHERE us.id = ? AND ss.id = s.id GROUP BY s.id HAVING COUNT(s.id) > 1) AND s.id = ? GROUP BY s.id HAVING COUNT(s.id) > 1)"),
		@NamedQuery(name = "User.getUserStickersByType", query = "SELECT u FROM User u JOIN FETCH u.stickers s WHERE u.id = ? AND s.type = ?"),
		@NamedQuery(name = "User.getUserStickersByNumber", query = "SELECT u FROM User u JOIN FETCH u.stickers s WHERE u.id = ? AND s.number = ?") })
public class User implements Serializable
{

	private static final long serialVersionUID = -5521321395949279437L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "AVATAR_URL")
	private String avatarUrl;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "LAST_LOGIN_DATE")
	private Date lastLoginDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RANK_ID")
	private Rank rank;

	@ManyToMany(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.ALL })
	private List<Sticker> stickers;

	@Transient
	private Long stickerCount;

	public User(Long id, Long count)
	{
		if (id != null && count != null)
		{
			this.id = id;
			this.stickerCount = count;
		}
	}

	public User(User u, Long count)
	{
		if (u != null)
		{
			this.id = u.id;
			this.name = u.name;
			this.avatarUrl = u.avatarUrl;
			this.creationDate = u.creationDate;
			this.lastLoginDate = u.lastLoginDate;
			this.rank = u.rank;
			this.stickerCount = count;
			Hibernate.initialize(u.stickers);
			this.stickers = u.stickers;
		}
	}

	public User()
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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the avatarUrl
	 */
	public String getAvatarUrl()
	{
		return avatarUrl;
	}

	/**
	 * @param avatarUrl
	 *            the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl)
	{
		this.avatarUrl = avatarUrl;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the rank
	 */
	public Rank getRank()
	{
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(Rank rank)
	{
		this.rank = rank;
	}

	/**
	 * @return the stickers
	 */
	public List<Sticker> getStickers()
	{
		return stickers;
	}

	/**
	 * @param stickers
	 *            the stickers to set
	 */
	public void setStickers(List<Sticker> stickers)
	{
		this.stickers = stickers;
	}

	/**
	 * @return the stickerCount
	 */
	public Long getStickerCount()
	{
		return stickerCount;
	}

	/**
	 * @param stickerCount
	 *            the stickerCount to set
	 */
	public void setStickerCount(Long stickerCount)
	{
		this.stickerCount = stickerCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
