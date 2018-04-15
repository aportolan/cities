package com.infinum.cities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinum.cities.domain.City;
import com.infinum.cities.repository.CityRepository;
import com.infinum.cities.representation.CityResource;
import com.infinum.cities.representation.CityResourceAssembler;
import com.infinum.cities.representation.SecuredResource;

@RestController
@RequestMapping("/secured/cities")
@ExposesResourceFor(FavoriteCitiesByUserController.class)
public class FavoriteCitiesByUserController {
	@Autowired
	private CityRepository cityRepository;
	private final CityResourceAssembler cityResourceAssembler = new CityResourceAssembler();

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CityResource> create(@RequestBody City city) {

		CityResource cr = cityResourceAssembler.toResource(cityRepository.save(city));
		return ResponseEntity.ok(cr);
	}

	@GetMapping(value = "/getFavorites", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CityResource>> getFavorites() {
		List<CityResource> cr = cityResourceAssembler.toResources(cityRepository.getFavorites());
		return ResponseEntity.ok(cr);
	}

	@PostMapping(value = "/createFavorite", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CityResource> addToFavorites(@RequestBody City city) {

		CityResource cr = cityResourceAssembler.toResource(cityRepository.addToFavorites(city));
		return ResponseEntity.ok(cr);
	}

	@DeleteMapping(value = "/deleteFavorite/{id}")
	public void deleteFavorite(@PathVariable Long id) {
		cityRepository.deleteFavorite(id);
	}
	
	@GetMapping("/")
	public ResponseEntity<SecuredResource> getRoot(){
		return ResponseEntity.ok(new SecuredResource());
	}
}
