package com.infinum.cities.representation;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.infinum.cities.domain.City;

import lombok.Data;


@Data
public class UserResource extends ResourceSupport {
	private Long idUser;
	private String mail;
	private String password;
	private String token;
	private List<City> listOfFavoritedCities;
	

}
