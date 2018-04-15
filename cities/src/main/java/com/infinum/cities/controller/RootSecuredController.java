package com.infinum.cities.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinum.cities.representation.SecuredResource;

@RestController
@RequestMapping("/secured")
public class RootSecuredController {

	@GetMapping("/")
	public ResponseEntity<SecuredResource> getSecuredRoot() {
		return ResponseEntity.ok(new SecuredResource());
	}

}
