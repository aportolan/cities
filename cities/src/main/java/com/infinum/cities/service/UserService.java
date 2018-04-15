package com.infinum.cities.service;

import com.infinum.cities.dto.SecUserDTO;

public interface UserService {

	SecUserDTO getUserByToken(String token);

	SecUserDTO getTokenAndUser(SecUserDTO user);
	
	SecUserDTO getUser(SecUserDTO user);
}
