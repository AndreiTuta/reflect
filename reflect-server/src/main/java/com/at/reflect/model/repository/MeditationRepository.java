package com.at.reflect.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.at.reflect.model.entity.meditation.Meditation;

/**
 *
 * @author at
 */
public abstract interface MeditationRepository  extends CrudRepository<Meditation, Integer> {
    
}