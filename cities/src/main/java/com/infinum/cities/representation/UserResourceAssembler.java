package com.infinum.cities.representation;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.infinum.cities.controller.RootController;
import com.infinum.cities.domain.User;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

	public UserResourceAssembler() {
		super(RootController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(User user) {

		UserResource resource = createResourceWithId(user.getIdUser(), user);
		resource.setIdUser(user.getIdUser());
		resource.setMail(user.getMail());
		resource.setPassword(user.getPassword());
		resource.setToken(user.getToken());
		resource.setListOfFavoritedCities(user.getListOfFavoritedCities());
		return resource;
	}
}
