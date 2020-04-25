package com.at.reflect.model.repository;

import com.at.reflect.model.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author at
 */
public abstract interface UserRepository extends CrudRepository<User, Integer> {
}
