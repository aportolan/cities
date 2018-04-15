package com.infinum.cities.representation;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.infinum.cities.controller.FavoriteCitiesByUserController;
import com.infinum.cities.domain.City;

public class CitySecuredResourceAssembler extends ResourceAssemblerSupport<City, CityResource> {

	public CitySecuredResourceAssembler() {
		super(FavoriteCitiesByUserController.class, CityResource.class);
	}

	@Override
	public CityResource toResource(City city) {

		CityResource resource = createResourceWithId(city.getIdCity(),city );
		resource.setCreationDate(city.getCreationDate());
		resource.setDescription(city.getDescription());
		resource.setIdCity(city.getIdCity());
		resource.setName(city.getName());
		resource.setPopulation(city.getPopulation());
		return resource;
	}
}
