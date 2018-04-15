package com.infinum.cities.representation;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;

import com.infinum.cities.controller.CityController;
import com.infinum.cities.controller.CityMostFavoritedController;

public class PublicResource extends ResourceSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicResource.class);

	public PublicResource() {
		Method method;
		try {
			method = CityController.class.getMethod("cities",ResponseEntity.class);
			this.add(ControllerLinkBuilder.linkTo(method).withSelfRel());
			method = CityController.class.getMethod("citiesOrderedByDate",ResponseEntity.class);
			this.add(ControllerLinkBuilder.linkTo(method).withSelfRel());
			method = CityMostFavoritedController.class.getMethod("citiesFavorited",ResponseEntity.class);
			this.add(ControllerLinkBuilder.linkTo(method).withSelfRel());

		} catch (NoSuchMethodException | SecurityException e) {
			LOGGER.error("Error while creating hateoas resource!", e);
		}

	}

}
