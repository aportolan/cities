package com.infinum.cities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinum.cities.dto.CityDTO;
import com.infinum.cities.repository.CityRepository;
import com.infinum.cities.representation.CityDTOResource;
import com.infinum.cities.representation.CityDTOResourceAssembler;

@RestController
@RequestMapping("/public/cities")
@ExposesResourceFor(CityDTOResource.class)
public class CityMostFavoritedController {
	@Autowired
	private CityRepository cityRepository;
	
	private final CityDTOResourceAssembler cityDTOResourceAssembler = new CityDTOResourceAssembler();



	@GetMapping(value = "/byMostFavorited", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CityDTOResource>> citiesFavorited() {
		List<CityDTO> citiesFavorited = cityRepository.findAllOrderByMostFavorited();

		List<CityDTOResource> cr = cityDTOResourceAssembler.toResources(citiesFavorited);
		return ResponseEntity.ok(cr);
	}
}
