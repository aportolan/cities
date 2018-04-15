package com.infinum.cities.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infinum.cities.domain.User;
import com.infinum.cities.exception.CitiesException;
import com.infinum.cities.repository.CustomUserRepository;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public User saveWithEncryptedPassword(User user) {
		if (user.getPassword() == null)
			throw new CitiesException("Password must not be empty!");
		entityManager.persist(user);
		return user;
	}

}
