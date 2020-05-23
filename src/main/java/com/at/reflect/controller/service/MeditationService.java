package com.at.reflect.controller.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.at.reflect.common.utils.JsonUtil;
import com.at.reflect.model.entity.Meditation;
import com.at.reflect.model.entity.SubMeditation;
import com.at.reflect.model.repository.MeditationRepository;
import com.at.reflect.model.repository.SubMeditationRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MeditationService implements Service {
	@Autowired
	private MeditationRepository meditationRepository;
	@Autowired
	private SubMeditationRepository subMeditationRepository;

	public String createNewMeditation(final String newMeditation) {
		String response = "";
		Meditation meditation = convertToMeditation(JsonParser.parseString(newMeditation).getAsJsonObject());
		if (meditation != null) {
			return meditation.toString();
		}
		return response;
	}

	private Meditation convertToMeditation(JsonObject newMeditation) {
		Meditation meditation = createNewMeditation(newMeditation.get("name"), newMeditation.get("duration"),
				newMeditation.get("address"), newMeditation.get("preview"),
				JsonUtil.toBoolean(newMeditation.get("isAvailable")),
				JsonUtil.toSubMeditationList(newMeditation.getAsJsonArray("subMeditations")));
		return meditation;
	}

	public Meditation createNewMeditation(final JsonElement name, final JsonElement duration, final JsonElement address,
			final JsonElement preview, final boolean isAvailable, final List<SubMeditation> subMeditations) {
		Meditation meditation = new Meditation();
		meditation.setName(name.getAsString());
		meditation.setDuration(duration.getAsString());
		meditation.setAddress(address.getAsString());
		meditation.setPreview(preview.getAsString());
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
	public void saveSubMeditationsForMeditation(final List<SubMeditation> subMeditations, Meditation meditation) {
		for (SubMeditation subMeditation : subMeditations) {
			createNewSubMeditation(meditation.getId(), subMeditation.getName(),
					subMeditation.getMeditationPlayerAdress(), subMeditation.getMeditationAudioadress());
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

	private List<SubMeditation> fetchSubMeditationsList(Integer id) {
		List<SubMeditation> meditationSubMeditations = StreamSupport
				.stream(subMeditationRepository.findAll().spliterator(), false)
				.filter(subMed -> subMed.getParentMeditationId() == id).collect(Collectors.toList());
		return meditationSubMeditations;
	}

	public void updateMeditation(final String meditationName, final String meditationDuration,
			final boolean isAvailable, Meditation meditation) {
		meditation.setName(meditationName);
		meditation.setDuration(meditationDuration);
		meditation.setAvailable(isAvailable);
		meditationRepository.save(meditation);
	}

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
