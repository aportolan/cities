package com.infinum.cities.representation;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTOResource extends ResourceSupport {

	private Long idCity;

	private String name;

	private String description;

	private Long population;

	private Date creationDate;

	private Long noOfFaves;

}
