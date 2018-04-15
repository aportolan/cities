package com.infinum.cities.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(name = User.TABLE, uniqueConstraints = @UniqueConstraint(columnNames = { "MAIL" }))
public class User {
	public static final String TABLE = "USER";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_USER", nullable = false)
	private Long idUser;
	@Column(name = "MAIL", nullable = false, length = 100)
	private String mail;
	@Column(name = "PASSWORD", nullable = false, length = 200)
	private String password;
	@Column(name = "TOKEN", nullable = false, length = 200)
	private String token;

	@JoinTable(name = "FAVORITE_CITIES_BY_USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID_USER",
			"ID_CITY" }) }, joinColumns = @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_CITY",unique=false, referencedColumnName = "ID_CITY"))
	@ManyToMany
	private List<City> listOfFavoritedCities;

}
