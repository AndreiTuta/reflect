package com.at.reflect.model.repository;

import com.at.reflect.model.entity.Meditation;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author at
 */
public abstract interface MeditationRepository  extends CrudRepository<Meditation, Integer> {
    
}