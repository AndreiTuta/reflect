package com.at.reflect.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.at.reflect.model.entity.Meditation;
import com.at.reflect.model.repository.MeditationRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author at
 */
@Controller
@RequestMapping(value = "/api/v1/meditation")
@Slf4j
public class MeditationController {

	@Autowired
	private MeditationRepository meditationRepository;
	// Constants
	private final String INVALID_CRED = "No param passed. Nothing has been executed";

	@PostMapping(value = "/addMeditation")
	@ResponseBody
	public ResponseEntity<String> addMeditation(@RequestParam final String meditationName,
			@RequestParam final String meditationDuration, @RequestParam final boolean isAvailable) {
		if (!StringUtils.isEmpty(meditationName)) {
			Meditation meditation = new Meditation();
			meditation.setName(meditationName);
			meditation.setDuration(meditationDuration);
			meditation.setAvailable(isAvailable);
			meditationRepository.save(meditation);
			return ResponseEntity.ok(converToResponse(meditation));
		}
		return ResponseEntity.ok(INVALID_CRED);

	}

	@GetMapping(value = "/getMeditation/{meditationName}")
	@ResponseBody
	public ResponseEntity<String> fetchMeditation(@PathVariable(value = "meditationName") String meditationName) {
		if (!StringUtils.isEmpty(meditationName)) {
			Meditation meditation = StreamSupport.stream(meditationRepository.findAll().spliterator(), false)
					.filter(med -> med.getName().equals(meditationName)).findAny().orElse(null);
			if (meditation != null) {
				return ResponseEntity.ok(converToResponse(meditation));
			} else {
				return ResponseEntity.ok("Failed returning a meditation.");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	@PutMapping(value = "/updateMeditation/{id}")
	@ResponseBody
	public ResponseEntity<String> udpateMeditation(@PathVariable(value = "id") final String id,
			@RequestParam final String meditationName, @RequestParam final String meditationDuration,
			@RequestParam final boolean isAvailable) {
		if (!StringUtils.isEmpty(id)) {
			Meditation meditation = meditationRepository.findById(Integer.valueOf(id)).orElse(null);
			if (meditation != null) {
				log.debug("Updating meditation {}", meditation.getId());
				meditation.setName(meditationName);
				meditation.setDuration(meditationDuration);
				meditation.setAvailable(isAvailable);
				meditationRepository.save(meditation);
				return ResponseEntity.ok(converToResponse(meditation));
			} else {
				return ResponseEntity.ok("Failed returning a meditation.");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	private String converToResponse(Meditation meditation) {
		return meditation.toString();
	}
}