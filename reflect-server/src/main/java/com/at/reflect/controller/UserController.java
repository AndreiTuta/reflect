package com.at.reflect.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public ResponseEntity<String> addUsers(@RequestParam(required = true) final String userName,
			@RequestParam(required = true) final String userPassword,
			@RequestParam(required = false) final String userEmail) {
		if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPassword)) {
			User user = userService.fetchUser(userName, userPassword, "");
			if (user != null) {
				return ResponseEntity.ok("User already exists");
			} else {
				User newUser = userService.createNewUser(userName, userPassword, userEmail);
				return ResponseEntity.ok(userService.convertToResponse(newUser));
			}
		}
		return ResponseEntity.ok(INVALID_CRED);

	}

	@GetMapping(value = "/getUser")
	@ResponseBody
	public ResponseEntity<String> logUsers(@RequestParam(required = true) final String userName,
			@RequestParam(required = true) final String userPassword,
			@RequestParam(required = false) final String userEmail, @RequestParam final boolean all) {
		if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPassword)) {
			User user = userService.fetchUser(userName, userPassword, "");
			if (user != null) {
				if (all) {
					return ResponseEntity.ok(userService.fetchAllUsers());
				}
				return ResponseEntity.ok(userService.convertToResponse(user));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	@PutMapping(value = "/updateUser")
	@ResponseBody
	public ResponseEntity<String> udpateUsers(@RequestParam(required = true) final String userName,
			@RequestParam(required = true) final String userPassword,
			@RequestParam(required = false) final String userEmail) {
		if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPassword)) {
			User user = userService.fetchUser(userName, userPassword, "");
			if (user != null) {
				User updatedUser = userService.updateExistingUser(userName, userPassword, userEmail, user);
				return ResponseEntity.ok(userService.convertToResponse(updatedUser));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}
}
