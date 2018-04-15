package com.infinum.cities.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = City.TABLE)
public class City {

	public static final String TABLE = "CITY";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CITY", nullable = false)
	private Long idCity;
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;
	@Column(name = "DESCRIPTION", nullable = false, length = 4000)
	private String description;
	@Column(name = "POPULATION", nullable = false)
	private Long population;
	@Column(name = "CREATION_DATE", nullable = false)
	@CreationTimestamp
	private Date creationDate;

}
