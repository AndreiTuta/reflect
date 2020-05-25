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
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.at.reflect.controller.service.UserService;
import com.at.reflect.model.entity.User;

@Controller
@RequestMapping(value = "/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	// Constants
	private final String INVALID_CRED = "No params passed. Nothing has been executed";

	@PostMapping(value = "/addUser")
	@ResponseBody
	public ResponseEntity<String> addUsers(@RequestBody final JSONObject userJson) {
		final String userJsonString = userJson.toString();
		if (!StringUtils.isEmpty(userJsonString)) {
			User user = userService.convertJsonToUser(userJsonString);
			if (user != null) {
				return ResponseEntity.ok("User already exists");
			} else {
				User newUser = userService.createUserFromJson(userJsonString);
				return ResponseEntity.ok(userService.convertToResponse(newUser));
			}
		}
		return ResponseEntity.ok(INVALID_CRED);

	}

	@GetMapping(value = "/getUser")
	@ResponseBody
	public ResponseEntity<String> logUsers(@RequestBody final JSONObject userPasswordJson) {
		final String userJsonString = userPasswordJson.toString();
		if (!StringUtils.isEmpty(userJsonString)) {
			User user = userService.convertJsonToUser(userJsonString);
			if (user != null) {
				return ResponseEntity.ok(userService.convertToResponse(user));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	@PutMapping(value = "/updateUser")
	@ResponseBody
	public ResponseEntity<String> udpateUsers(@RequestBody final JSONObject userJson) {
		final String userJsonString = userJson.toString();
		if (!StringUtils.isEmpty(userJsonString)) {
			User user = userService.convertJsonToUser(userJsonString, true);
			if (user != null) {
				User updatedUser = userService.updateExistingUser(userJsonString, user);
				return ResponseEntity.ok(userService.convertToResponse(updatedUser));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}
}
