package com.infinum.cities.dto;

import com.infinum.cities.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecUserDTO {

	private String mail;
	private String password;
	private String token;

	public SecUserDTO(User user) {
		super();
		this.mail = user.getMail();
		this.password = user.getPassword();
		this.token = user.getToken();
	}

}
