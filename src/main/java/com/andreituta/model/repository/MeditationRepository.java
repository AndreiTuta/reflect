package com.andreituta.model.repository;

import com.andreituta.model.Meditation;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author at
 */
public abstract interface MeditationRepository  extends CrudRepository<Meditation, Integer> {
    
}