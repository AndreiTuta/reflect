package com.at.reflect.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.at.reflect.model.entity.user.User;

/**
 * @author at
 */
public abstract interface UserRepository extends CrudRepository<User, Integer> {
}
