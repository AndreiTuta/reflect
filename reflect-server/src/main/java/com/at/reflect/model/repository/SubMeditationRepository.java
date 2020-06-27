package com.at.reflect.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.at.reflect.model.entity.meditation.SubMeditation;

public abstract interface SubMeditationRepository extends CrudRepository<SubMeditation, Integer> {

}
