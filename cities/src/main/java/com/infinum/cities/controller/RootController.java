package com.infinum.cities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinum.cities.dto.SecUserDTO;
import com.infinum.cities.representation.PublicResource;
import com.infinum.cities.representation.RootResource;
import com.infinum.cities.security.HeaderToken;
import com.infinum.cities.service.UserService;

@RestController
@RequestMapping("/")
public class RootController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	private ResponseEntity<SecUserDTO> token(@RequestBody SecUserDTO user) {
		user = userService.getTokenAndUser(user);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HeaderToken.X_AUTH_TOKEN.getValue(), user.getToken());
		responseHeaders.set(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE);
		ResponseEntity<SecUserDTO> response = new ResponseEntity<SecUserDTO>(user, responseHeaders, HttpStatus.OK);

		return response;
	}

	@GetMapping("/")
	public ResponseEntity<RootResource> getRoot() {
		return ResponseEntity.ok(new RootResource());
	}



	@GetMapping("/public")
	public ResponseEntity<PublicResource> getPublicRoot() {
		return ResponseEntity.ok(new PublicResource());
	}

}
