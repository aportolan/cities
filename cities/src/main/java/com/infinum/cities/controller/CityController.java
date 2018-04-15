package com.infinum.cities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinum.cities.domain.City;
import com.infinum.cities.repository.CityRepository;
import com.infinum.cities.representation.CityResource;
import com.infinum.cities.representation.CityResourceAssembler;
import com.infinum.cities.representation.PublicResource;

@RestController
@RequestMapping("/public/cities")
@ExposesResourceFor(CityResource.class)
public class CityController {
	@Autowired
	private CityRepository cityRepository;
	
	private final CityResourceAssembler cityResourceAssembler = new CityResourceAssembler();


	@GetMapping( produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CityResource>> cities() {
		List<City> cities = cityRepository.findAll();
		List<CityResource> cr = cityResourceAssembler.toResources(cities);
		return ResponseEntity.ok(cr);
	}

	@GetMapping(value = "/orderedByDate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CityResource>> citiesOrderedByDate() {
		List<City> citiesOrderedByDate = cityRepository.findAllOrderByCreationDateDesc();
		List<CityResource> cr = cityResourceAssembler.toResources(citiesOrderedByDate);
		return ResponseEntity.ok(cr);

	}
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CityResource> getSingle(Long id) {
		City city = cityRepository.findById(id).orElse(new City());
		CityResource cr = cityResourceAssembler.toResource(city);
		return ResponseEntity.ok(cr);

	}
	@GetMapping("/")
	public ResponseEntity<PublicResource> getRoot(){
		return ResponseEntity.ok(new PublicResource());
	}

}
