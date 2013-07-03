package com.medialab.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CacheModeType;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "PLAYER")
@NamedQueries({
	@NamedQuery(name = "Player.getByName", query = "SELECT p FROM Player p WHERE p.name = ?", cacheable = true, cacheMode = CacheModeType.NORMAL, cacheRegion = "com.medialab.persistence.entity.Player"),
	@NamedQuery(name = "Player.getByStickerId", query = "SELECT p FROM Player p INNER JOIN p.sticker s WHERE s.id = ?", cacheable = true, cacheMode = CacheModeType.NORMAL, cacheRegion = "com.medialab.persistence.entity.Player"),
	@NamedQuery(name = "Player.getByStickerNumber", query = "SELECT p FROM Player p INNER JOIN p.sticker s WHERE s.number = ?", cacheable = true, cacheMode = CacheModeType.NORMAL, cacheRegion = "com.medialab.persistence.entity.Player"),
	@NamedQuery(name = "Player.getByTeamId", query = "SELECT p FROM Player p INNER JOIN p.team t WHERE t.id = ?", cacheable = true, cacheMode = CacheModeType.NORMAL, cacheRegion = "com.medialab.persistence.entity.Player")
})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.medialab.persistence.entity.Player")
public class Player implements Serializable {

	private static final long serialVersionUID = -6580348386659611252L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEAM_ID")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.medialab.persistence.entity.Team")
	private Team team;

	@Column(name = "NAME")
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STICKER_ID")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.medialab.persistence.entity.Sticker")
	private Sticker sticker;

	public Player() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * @return the sticker
	 */
	public Sticker getSticker() {
		return sticker;
	}

	/**
	 * @param sticker the sticker to set
	 */
	public void setSticker(Sticker sticker) {
		this.sticker = sticker;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Player other = (Player) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (team == null)
		{
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	
	

}
