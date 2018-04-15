package com.infinum.cities.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infinum.cities.domain.City;
import com.infinum.cities.domain.User;
import com.infinum.cities.repository.CityRepository;
import com.infinum.cities.repository.UserRepository;

@Service
public class MockData {

	private static final String DEFAULT_PASSWORD = "password";
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(MockData.class);

	@Transactional
	public void mock() {

		List<City> cities = new ArrayList<>();
Calendar calendar = Calendar.getInstance();

		for (int i = 0; i <= 100; i++) {
			City city = new City();
			calendar.set(Calendar.YEAR, 1980 + RandomUtils.nextInt(1, 38));
			calendar.set(Calendar.MONTH,RandomUtils.nextInt(1, 12));
			calendar.set(Calendar.DAY_OF_MONTH,RandomUtils.nextInt(1, 28));
			city.setCreationDate(calendar.getTime());
			
			city.setName("City " + RandomStringUtils.randomAlphabetic(10));
			city.setDescription("Description" + RandomStringUtils.randomAlphabetic(15) + " about " + city.getName());
			city.setPopulation(RandomUtils.nextLong(10000, 1000000));
			cityRepository.save(city);
			cities.add(city);
			User user = new User();
			user.setMail(RandomStringUtils.randomAlphabetic(15) + '@' + RandomStringUtils.randomAlphabetic(5) + ".com");
			user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
			user.setToken(RandomStringUtils.randomAlphabetic(50));
			user.setListOfFavoritedCities(new ArrayList<>());

			user.getListOfFavoritedCities().addAll(cities.size() > 30 ? cities.subList(RandomUtils.nextInt(0, 10),RandomUtils.nextInt(15, 30))
					: cities.subList(RandomUtils.nextInt(0, i),i));
			userRepository.save(user);


			LOGGER.debug("Added user:{}", user.getMail());
		}

	}
}
