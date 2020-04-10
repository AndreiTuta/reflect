package com.andreituta.model.repository;

import com.andreituta.model.User;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public abstract interface UserRepository extends CrudRepository<User, Integer> {
}
