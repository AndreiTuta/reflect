package com.at.reflect.model.repository;

import com.at.reflect.model.entity.UserMeditation;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author at
 */
public abstract interface UserMeditationRepository extends CrudRepository<UserMeditation, Integer> {

}
