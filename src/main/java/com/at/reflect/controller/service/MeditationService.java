package com.at.reflect.controller.service;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.at.reflect.model.entity.Meditation;
import com.at.reflect.model.repository.MeditationRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MeditationService implements Service {
	@Autowired
	private MeditationRepository meditationRepository;

	public Meditation createNewMeditation(final String meditationName, final String meditationDuration,
			final boolean isAvailable) {
		Meditation meditation = new Meditation();
		meditation.setName(meditationName);
		meditation.setDuration(meditationDuration);
		meditation.setAvailable(isAvailable);
		meditationRepository.save(meditation);
		return meditation;
	}

	public Meditation fetchMeditationByName(final String meditationName) {
		Meditation meditation = StreamSupport.stream(meditationRepository.findAll().spliterator(), false)
				.filter(med -> med.getName().equals(meditationName)).findAny().orElse(null);
		return meditation;
	}

	/**
	 * @param meditationName
	 * @param meditationDuration
	 * @param isAvailable
	 * @param meditation
	 */
	public void updateMeditation(final String meditationName, final String meditationDuration,
			final boolean isAvailable, Meditation meditation) {
		meditation.setName(meditationName);
		meditation.setDuration(meditationDuration);
		meditation.setAvailable(isAvailable);
		meditationRepository.save(meditation);
	}

	/**
	 * @param id
	 * @return
	 */
	public Meditation fetchMeditationById(final String id) {
		Meditation meditation = meditationRepository.findById(Integer.valueOf(id)).orElse(null);
		return meditation;
	}

	public String converToResponse(Meditation meditation) {
		return meditation.toString();
	}

	@Override
	public ServiceType getType() {
		return ServiceType.MED;
	}
}
