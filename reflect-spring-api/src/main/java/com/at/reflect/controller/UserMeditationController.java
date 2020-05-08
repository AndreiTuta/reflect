package com.at.reflect.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.at.reflect.model.entity.Meditation;
import com.at.reflect.model.entity.User;
import com.at.reflect.model.entity.UserMeditation;
import com.at.reflect.model.repository.MeditationRepository;
import com.at.reflect.model.repository.UserMeditationRepository;
import com.at.reflect.model.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author at
 */
@Controller
@Slf4j
@RequestMapping(value = "/api/v1/meditation/user")
public class UserMeditationController {

	@Autowired
	private MeditationRepository meditationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMeditationRepository userMeditationRepository;
	// Constants
	private final String INVALID_CRED = "No param passed. Nothing has been executed";

	@PostMapping(value = "/addUserMeditation/{userId}/{meditationId}")
	@ResponseBody
	public ResponseEntity<String> addMeditation(@PathVariable(value = "meditationId") final String meditationId,
			@PathVariable(value = "userId") final String userId) {
		if (!StringUtils.isEmpty(meditationId) && !StringUtils.isEmpty(userId)) {
			Meditation meditation = meditationRepository.findById(Integer.valueOf(meditationId)).orElse(null);
			User user = userRepository.findById(Integer.valueOf(userId)).orElse(null);
			if (meditation != null && user != null) {
				log.debug("Adding meditation {} for user", meditation.getId(), userId);
				UserMeditation userMeditation = new UserMeditation();
				userMeditation.setMeditationId(meditation.getId());
				userMeditation.setUserId(user.getId());
				userMeditationRepository.save(userMeditation);
				return ResponseEntity.ok(convertMeditationToResponse(userMeditation));
			} else {
				return ResponseEntity.ok("Failed added info for provided info.");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	@GetMapping(value = "/fetchUserMeditations/{userId}")
	@ResponseBody
	public ResponseEntity<String> fetchUserMeditations(@PathVariable(value = "userId") String userId,
			@RequestParam boolean selectAvailable) {
		if (!StringUtils.isEmpty(userId)) {
			User user = userRepository.findById(Integer.valueOf(userId)).orElse(null);
			if (user != null) {
				if (selectAvailable) {
					List<Meditation> availableMeditations = StreamSupport
							.stream(meditationRepository.findAll().spliterator(), false)
							.filter(med -> med.isAvailable()).collect(Collectors.toList());
					return ResponseEntity.ok(convertMeditationListToResponse(availableMeditations));
				} else {
					List<UserMeditation> userMeditations = StreamSupport
							.stream(userMeditationRepository.findAll().spliterator(), false)
							.filter(med -> user.getId() == med.getUserId()).collect(Collectors.toList());
					return ResponseEntity.ok(convertUserMeditationListToResponse(userMeditations));
				}
			} else {
				return ResponseEntity.ok("Failed added info for provided info.");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	@GetMapping(value = "/fetchUserMeditations/{userId}/{meditationId}")
	@ResponseBody
	public ResponseEntity<String> fetchUserSpecificMeditation(
			@PathVariable(value = "meditationId") final String meditationId,
			@PathVariable(value = "userId") final String userId) {
		if (!StringUtils.isEmpty(meditationId) && !StringUtils.isEmpty(userId)) {
			UserMeditation userMeditation = StreamSupport
					.stream(userMeditationRepository.findAll().spliterator(), false)
					.filter(userMed -> userMed.getMeditationId() == Integer.valueOf(meditationId)
							&& userMed.getUserId() == Integer.valueOf(userId))
					.findAny().orElse(null);
			if (userMeditation != null) {
				return ResponseEntity.ok(convertMeditationToResponse(userMeditation));
			} else {
				return ResponseEntity.ok("Failed added info for provided info.");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	private String convertMeditationListToResponse(List<Meditation> availableMeditations) {
		return availableMeditations.toString();
	}

	private String convertUserMeditationListToResponse(List<UserMeditation> availableMeditations) {
		return availableMeditations.toString();
	}

	private String convertMeditationToResponse(UserMeditation userMeditation) {
		return userMeditation.toString();
	}
}