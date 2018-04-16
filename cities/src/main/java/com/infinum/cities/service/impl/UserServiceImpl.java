package com.infinum.cities.service.impl;

import javax.jdo.annotations.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinum.cities.domain.QUser;
import com.infinum.cities.domain.User;
import com.infinum.cities.dto.SecUserDTO;
import com.infinum.cities.exception.CitiesException;
import com.infinum.cities.repository.UserRepository;
import com.infinum.cities.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public SecUserDTO getUserByToken(String token) {

		User user = userRepository.findOne(QUser.user.token.eq(token)).orElse(new User());
		//if (user != null) {
		//	user.setToken(RandomStringUtils.randomAlphabetic(50));
		//	userRepository.save(user);
		//}

		return new SecUserDTO(user);
	}

	@Override
	@Transactional
	public SecUserDTO getTokenAndUser(SecUserDTO user) {
		User userDB = userRepository.findOne(QUser.user.mail.eq(user.getMail())).orElse(new User());
		if (userDB.getPassword() == null) {
			boolean noMandatoryData = user.getPassword() == null || user.getMail() == null;
			if (noMandatoryData)
				throw new CitiesException("Request must contain email and password!");
			userDB.setToken(RandomStringUtils.randomAlphabetic(50));
			userDB.setPassword(user.getPassword());
			userDB.setMail(user.getMail());
			userRepository.save(userDB);
		}
		return new SecUserDTO(userDB);
	}

	@Override
	public SecUserDTO getUser(SecUserDTO user) {
		boolean noMandatoryData = user.getPassword() == null || user.getMail() == null;
		if (noMandatoryData)
			throw new CitiesException("Request must contain email and password!");
		return new SecUserDTO(userRepository.findOne(QUser.user.mail.eq(user.getMail())).orElse(new User()));
	}

}
