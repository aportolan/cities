package com.infinum.cities.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO{

	private Long idCity;

	private String name;

	private String description;

	private Long population;

	private Date creationDate;

	private Long noOfFaves;

}
