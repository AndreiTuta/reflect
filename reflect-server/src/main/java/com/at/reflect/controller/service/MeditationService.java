package com.at.reflect.controller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.at.reflect.model.entity.meditation.Meditation;
import com.at.reflect.model.entity.meditation.SubMeditation;
import com.at.reflect.model.repository.MeditationRepository;
import com.at.reflect.model.repository.SubMeditationRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MeditationService implements Service {
	@Autowired
	private MeditationRepository meditationRepository;
	@Autowired
	private SubMeditationRepository subMeditationRepository;

	public Meditation createNewMeditation(final String meditationName, final String mediatationDuration,
			final String mediatationAddress, final String previewAddress, final boolean isAvailable,
			final ArrayList<SubMeditation> subMeditations) {
		Meditation meditation = new Meditation();
		meditation.setName(meditationName);
		meditation.setDuration(mediatationDuration);
		meditation.setAddress(mediatationAddress);
		meditation.setPreview(previewAddress);
		meditation.setAvailable(isAvailable);
		meditation.setNumMed(subMeditations.size());
		meditation = meditationRepository.save(meditation);
		saveSubMeditationsForMeditation(subMeditations, meditation);
		meditation.setSubmeditations(fetchSubMeditationsList(meditation.getId()));
		return meditation;
	}

	/**
	 * @param subMeditations
	 * @param meditation
	 */
	public void saveSubMeditationsForMeditation(final List<SubMeditation> subMeditations, final Meditation meditation) {
		if (!subMeditations.isEmpty()) {
			for (SubMeditation subMeditation : subMeditations) {
				createNewSubMeditation(meditation.getId(), subMeditation.getName(),
						subMeditation.getMeditationPlayerAdress(), subMeditation.getMeditationAudioadress());
			}
		}
	}

	public SubMeditation createNewSubMeditation(final Integer parentMeditationId, final String name,
			final String meditationPlayerAdress, final String meditationAudioadress) {
		SubMeditation subMeditation = new SubMeditation();
		subMeditation.setParentMeditationId(parentMeditationId);
		subMeditation.setName(name);
		subMeditation.setMeditationPlayerAdress(meditationPlayerAdress);
		subMeditation.setMeditationAudioadress(meditationAudioadress);
		subMeditationRepository.save(subMeditation);
		return subMeditation;
	}

	public Meditation fetchMeditationByName(final String meditationName) {
		Meditation meditation = fetchMeditation(meditationName);
		return meditation;
	}

	public Meditation fetchMeditation(final String meditationName) {
		Meditation meditation = StreamSupport.stream(meditationRepository.findAll().spliterator(), false)
				.filter(med -> med.getName().equals(meditationName)).findAny().orElse(null);
		if (meditation != null) {
			meditation.setSubmeditations(fetchSubMeditationsList(meditation.getId()));
		}
		return meditation;
	}

	private List<SubMeditation> fetchSubMeditationsList(final Integer id) {
		List<SubMeditation> meditationSubMeditations = StreamSupport
				.stream(subMeditationRepository.findAll().spliterator(), false)
				.filter(subMed -> subMed.getParentMeditationId() == id).collect(Collectors.toList());
		return meditationSubMeditations;
	}

	public Meditation fetchMeditationById(final String id) {
		Meditation meditation = meditationRepository.findById(Integer.valueOf(id)).orElse(null);
		return meditation;
	}

	public Meditation updateMeditation(String updatedMeditationName, String updatedMediatationDuration,
			String updatedMediatationAddress, String updatedMeditationPreviewAddress, boolean isAvailable,
			final ArrayList<SubMeditation> updatedSubMeditations, Meditation meditation) {
		meditation.setName(updatedMeditationName);
		meditation.setDuration(updatedMediatationDuration);
		meditation.setAddress(updatedMediatationAddress);
		meditation.setPreview(updatedMeditationPreviewAddress);
		meditation.setAvailable(isAvailable);
		saveSubMeditationsForMeditation(updatedSubMeditations, meditation);
		meditationRepository.save(meditation);
		return meditation;
	}

	@Override
	public ServiceType getType() {
		return ServiceType.MED;
	}

}
