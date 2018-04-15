package com.infinum.cities.representation;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class PublicResource extends ResourceSupport {

	public PublicResource() {
		this.add(new Link("/cities/orderedByDate", "self"));
		this.add(new Link("/cities/?", "self"));
		this.add(new Link("/cities/", "self"));
		this.add(new Link("/cities/byMostFavorited", "self"));

	}

}
