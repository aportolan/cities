package com.infinum.cities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.infinum.cities.domain.City;

public interface CityRepository
		extends JpaRepository<City, Long>, QuerydslPredicateExecutor<City>, CustomCityRepository {





}
