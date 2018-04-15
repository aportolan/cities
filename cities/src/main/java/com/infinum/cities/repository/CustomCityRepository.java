package com.infinum.cities.repository;

import java.util.List;

import com.infinum.cities.domain.City;
import com.infinum.cities.dto.CityDTO;

public interface CustomCityRepository {

	List<CityDTO> findAllOrderByMostFavorited();

	List<City> findAllOrderByCreationDateDesc();

	City addToFavorites(City city);

	void deleteFavorite(Long id);

	List<City> getFavorites();
}
