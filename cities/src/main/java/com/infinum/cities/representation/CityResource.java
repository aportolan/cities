package com.infinum.cities.representation;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;

@Data
public class CityResource extends ResourceSupport {

	private Long idCity;
	private String name;
	private String description;
	private Long population;
	private Date creationDate;


}
