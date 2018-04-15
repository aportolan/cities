package com.infinum.cities.representation;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class RootResource extends ResourceSupport{
	
	public RootResource() {
		this.add(new Link("/public","self"));
		this.add(new Link("/secured","self"));
		this.add(new Link("/login","self"));
		this.add(new Link("/token","self"));
	}

}
