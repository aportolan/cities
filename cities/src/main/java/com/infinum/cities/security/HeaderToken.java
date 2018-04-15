package com.infinum.cities.security;

public enum HeaderToken {
	X_AUTH_TOKEN("X-Auth-Token");

	private final String value;

	private HeaderToken(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
