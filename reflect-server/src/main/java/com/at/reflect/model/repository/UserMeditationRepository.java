package com.at.reflect.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.at.reflect.model.entity.meditation.UserMeditation;

/**
 *
 * @author at
 */
public abstract interface UserMeditationRepository extends CrudRepository<UserMeditation, Integer> {

}
