package com.infinum.cities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.infinum.cities.domain.User;

public interface UserRepository extends JpaRepository<User,Long>, QuerydslPredicateExecutor<User>,CustomUserRepository{



}
