package com.infinum.cities.repository;

import com.infinum.cities.domain.User;

public interface CustomUserRepository {

	User saveWithEncryptedPassword(User user);
}
