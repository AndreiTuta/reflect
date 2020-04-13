package com.andreituta.model.repository;

import com.andreituta.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author at
 */
public abstract interface UserRepository extends CrudRepository<User, Integer> {
}
