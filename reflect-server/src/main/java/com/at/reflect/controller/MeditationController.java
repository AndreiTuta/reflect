package com.at.reflect.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.at.reflect.controller.service.MeditationService;
import com.at.reflect.model.entity.meditation.Meditation;

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

	@PostMapping(value = "/{meditationId}")
	@ResponseBody
	public ResponseEntity<?> addMeditation(@PathVariable(required = true) String meditationId,
			@RequestParam(required = true) final String meditationName,
			@RequestParam(required = true) final String mediatationDuration,
			@RequestParam(required = true) final String mediatationAddress,
			@RequestParam(required = true) final String mediatationNumMed,
			@RequestParam(required = true) final String previewAddress,
			@RequestParam(required = true) final String isAvailable) throws Exception {
		if (!StringUtils.isEmpty(meditationName)) {
			Meditation meditation = meditationService.fetchMeditationById(meditationId);
			if (meditation == null) {
				Meditation newMeditation = meditationService.createNewMeditation(meditationName, mediatationDuration,
						mediatationAddress, previewAddress, Boolean.valueOf(isAvailable),
						new ArrayList<>(Integer.valueOf(mediatationNumMed)));
				return ResponseEntity.ok(newMeditation);
			}
		}
		throw new Exception();
	}

	@GetMapping(value = "/{meditationId}")
	@ResponseBody
	public ResponseEntity<?> fetchMeditation(@PathVariable(required = true) String meditationId) throws Exception {
		if (!StringUtils.isEmpty(meditationId)) {
			Meditation meditation = meditationService.fetchMeditationById(meditationId);
			if (meditation != null) {
				return ResponseEntity.ok(meditation);
			}
		}
		throw new Exception();
	}

	@PutMapping(value = "/{meditationId}")
	@ResponseBody
	public ResponseEntity<?> udpateMeditation(@PathVariable(required = true) String meditationId,
			@RequestParam(required = false, defaultValue = "") final String meditationName,
			@RequestParam(required = false, defaultValue = "") final String mediatationDuration,
			@RequestParam(required = false, defaultValue = "") final String mediatationAddress,
			@RequestParam(required = false, defaultValue = "") final String previewAddress,
			@RequestParam(required = false, defaultValue = "") final String isAvailable,
			@RequestParam(required = false, defaultValue = "") final String updatedMeditationName,
			@RequestParam(required = false, defaultValue = "") final String updatedMediatationDuration,
			@RequestParam(required = false, defaultValue = "") final String updatedMediatationAddress,
			@RequestParam(required = false, defaultValue = "") final String updatedMeditationPreviewAddress,
			@RequestParam(required = false, defaultValue = "0") final String updatedMediatationNumMed,
			@RequestParam(required = false, defaultValue = "") final String updatedIsAvailable) throws Exception {
		if (!StringUtils.isEmpty(updatedMeditationName)) {
			Meditation meditation = meditationService.fetchMeditationById(meditationId);
			if (meditation != null) {
				log.debug("Updating meditation {}", meditation.getId());
				return ResponseEntity.ok(meditationService.updateMeditation(updatedMeditationName,
						updatedMediatationDuration, updatedMediatationAddress, updatedMeditationPreviewAddress,
						Boolean.valueOf(updatedIsAvailable), new ArrayList<>(Integer.valueOf(updatedMediatationNumMed)),
						meditation));
			}
		}
		throw new Exception();
	}
}
