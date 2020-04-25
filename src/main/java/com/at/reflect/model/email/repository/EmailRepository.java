package com.at.reflect.model.email.repository;

import com.at.reflect.model.entity.Email;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author at
 */
public abstract interface EmailRepository extends CrudRepository<Email, Integer> {

}
