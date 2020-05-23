package com.at.reflect.controller;

import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.at.reflect.controller.service.MeditationService;
import com.at.reflect.model.entity.Meditation;

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
	private MeditationService meditationService;
	// Constants
	private final String INVALID_CRED = "No param passed. Nothing has been executed";

	@PostMapping(value = "/addMeditation")
	@ResponseBody
	public ResponseEntity<String> addMeditation(@RequestBody final JSONObject meditationJson) {
		final String medJsonString = meditationJson.toString();
		if (!StringUtils.isEmpty(medJsonString)) {
			String meditation = meditationService.createNewMeditation(medJsonString);
			return ResponseEntity.ok(meditation.toString());
		}
		return ResponseEntity.ok(INVALID_CRED);

	}

	@GetMapping(value = "/getMeditation")
	@ResponseBody
	public ResponseEntity<String> fetchMeditation(@RequestParam final String meditationName) {
		if (!StringUtils.isEmpty(meditationName)) {
			Meditation meditation = meditationService.fetchMeditationByName(meditationName);
			if (meditation != null) {
				return ResponseEntity.ok(meditationService.converToResponse(meditation));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	@PutMapping(value = "/updateMeditation")
	@ResponseBody
	public ResponseEntity<String> udpateMeditation(@RequestParam final String id,
			@RequestParam final String meditationName, @RequestParam final String meditationDuration,
			@RequestParam final boolean isAvailable) {
		if (!StringUtils.isEmpty(id)) {
			Meditation meditation = meditationService.fetchMeditationById(id);
			if (meditation != null) {
				log.debug("Updating meditation {}", meditation.getId());
				meditationService.updateMeditation(meditationName, meditationDuration, isAvailable, meditation);
				return ResponseEntity.ok(meditationService.converToResponse(meditation));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}
}
