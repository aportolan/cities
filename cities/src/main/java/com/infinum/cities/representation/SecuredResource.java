package com.infinum.cities.representation;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class SecuredResource extends ResourceSupport {

	public SecuredResource() {

		this.add(new Link("/cities/create", "self"));
		this.add(new Link("/cities/getFavorites", "self"));
		this.add(new Link("/cities/createFavorite", "self"));
		this.add(new Link("/cities/deleteFavorite/?", "self"));

	}

}
