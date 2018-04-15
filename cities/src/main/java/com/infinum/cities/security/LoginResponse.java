package com.infinum.cities.security;

import lombok.Data;

@Data
public class LoginResponse {

	private boolean loggedIn = true;
	private String error;

}
