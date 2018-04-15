package com.infinum.cities.representation;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.infinum.cities.controller.CityMostFavoritedController;
import com.infinum.cities.dto.CityDTO;

public class CityDTOResourceAssembler extends ResourceAssemblerSupport<CityDTO, CityDTOResource> {

	public CityDTOResourceAssembler() {
		super(CityMostFavoritedController.class, CityDTOResource.class);
	}

	@Override
	public CityDTOResource toResource(CityDTO city) {

		CityDTOResource resource = createResourceWithId(city.getIdCity(),city );
		resource.setCreationDate(city.getCreationDate());
		resource.setDescription(city.getDescription());
		resource.setIdCity(city.getIdCity());
		resource.setName(city.getName());
		resource.setPopulation(city.getPopulation());
		return resource;
	}
}
