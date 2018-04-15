package com.infinum.cities.representation;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.infinum.cities.controller.FavoriteCitiesByUserController;
import com.infinum.cities.domain.City;

public class SecuredResource extends ResourceSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublicResource.class);

	public SecuredResource() {
		Method method;
		try {
			method = FavoriteCitiesByUserController.class.getMethod("create",City.class);
			this.add(ControllerLinkBuilder.linkTo(method).withSelfRel());
			method = FavoriteCitiesByUserController.class.getMethod("getFavorites");
			this.add(ControllerLinkBuilder.linkTo(method).withSelfRel());
			method = FavoriteCitiesByUserController.class.getMethod("addToFavorites");
			this.add(ControllerLinkBuilder.linkTo(method).withSelfRel());
			method = FavoriteCitiesByUserController.class.getMethod("deleteFavorite",Long.class);
			this.add(ControllerLinkBuilder.linkTo(method).slash("?").withSelfRel());

		} catch (NoSuchMethodException | SecurityException e) {
			LOGGER.error("Error while creating hateoas resource!", e);
		}

	}

}
