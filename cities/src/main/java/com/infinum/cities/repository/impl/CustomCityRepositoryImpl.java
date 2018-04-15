package com.infinum.cities.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.infinum.cities.domain.City;
import com.infinum.cities.domain.QCity;
import com.infinum.cities.domain.QUser;
import com.infinum.cities.domain.User;
import com.infinum.cities.dto.CityDTO;
import com.infinum.cities.repository.CustomCityRepository;
import com.infinum.cities.security.UserDetailsImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CustomCityRepositoryImpl implements CustomCityRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<CityDTO> findAllOrderByMostFavorited() {
		QCity city = QCity.city;
		QUser user = QUser.user;
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
		NumberPath<Long> aliasNoOfFaves = Expressions.numberPath(Long.class, "no_of_faves");

		return jpaQueryFactory
				.select(Projections.bean(CityDTO.class, city.idCity, city.name, city.description, city.creationDate,
						user.idUser.count().as(aliasNoOfFaves)))
				.from(user).innerJoin(user.listOfFavoritedCities, city)
				.groupBy(city.idCity, city.name, city.creationDate).orderBy(aliasNoOfFaves.desc()).fetch();

	}

	@Override
	public List<City> findAllOrderByCreationDateDesc() {
		QCity city = QCity.city;
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
		return jpaQueryFactory.select(city).from(city).orderBy(city.creationDate.desc()).fetch();
	}

	@Override
	@Transactional
	public City addToFavorites(City city) {

		QCity qCity = QCity.city;
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

		User user = getUser(jpaQueryFactory);
		city = jpaQueryFactory.select(qCity).from(qCity).where(qCity.name.eq(city.getName())).fetchFirst();
		if (user.getListOfFavoritedCities().isEmpty())
			user.setListOfFavoritedCities(new ArrayList<>());
		user.getListOfFavoritedCities().add(city);

		entityManager.persist(user);
		user.getListOfFavoritedCities().add(city);
		return jpaQueryFactory.select(qCity).from(qCity).where(qCity.idCity.eq(city.getIdCity())).fetchFirst();
	}

	@Override
	public void deleteFavorite(Long id) {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

		User user = getUser(jpaQueryFactory);
		user.setListOfFavoritedCities(user.getListOfFavoritedCities().stream()
				.filter(t -> t.getIdCity().compareTo(id) != 0).collect(Collectors.toCollection(ArrayList::new)));
		entityManager.persist(user);
		//new JPADeleteClause(entityManager, QUser.user.listOfFavoritedCities.any())
		//		.where(QUser.user.idUser.eq(user.getIdUser()).and(QUser.user.listOfFavoritedCities.any().idCity.eq(id)))
		//		.execute();

	}

	@Override
	public List<City> getFavorites() {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
		return jpaQueryFactory.select(QCity.city).from(QUser.user).innerJoin(QUser.user.listOfFavoritedCities, QCity.city)
				.where(QUser.user.idUser.eq(getUser(jpaQueryFactory).getIdUser())).fetch();
	}

	private User getUser(JPAQueryFactory jpaQueryFactory) {
		QUser qUser = QUser.user;
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return jpaQueryFactory.select(qUser).from(qUser).where(qUser.mail.equalsIgnoreCase(userDetails.getUsername()))
				.fetchFirst();
	}

}
