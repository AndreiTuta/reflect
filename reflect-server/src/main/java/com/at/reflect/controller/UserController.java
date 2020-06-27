package com.at.reflect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.at.reflect.controller.service.UserService;
import com.at.reflect.model.entity.model.user.User;

@Controller
@RequestMapping(value = "/api/v1/")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/user")
	@ResponseBody
	public ResponseEntity<User> addUsers(@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String userPassword) {
		User user = userService.validateReqParams(userEmail, userPassword);
		if (user != null) {
			return ResponseEntity.ok(userService.createNewUser(userEmail, userPassword));
		}
		return new ResponseEntity<User>(HttpStatus.CONFLICT);
	}

	@GetMapping(value = "/users")
	@ResponseBody
	public ResponseEntity<List<User>> logUsers(@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String userPassword) {
		User user = userService.validateReqParams(userEmail, userPassword);
		if (user != null) {
			return ResponseEntity.ok(userService.fetchAllUsers());
		} else {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/user")
	@ResponseBody
	public ResponseEntity<User> udpateUsers(@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String userPassword,
			@RequestParam(required = false, defaultValue = "") final String updatedUserEmail,
			@RequestParam(required = false, defaultValue = "") final String updatedUserPassword,
			@RequestParam(required = false, defaultValue = "") final String updatedUserName) {
		User user = userService.validateReqParams(userEmail, userPassword);
		if (user != null) {
			return ResponseEntity
					.ok(userService.updateExistingUser(updatedUserEmail, updatedUserPassword, updatedUserName, user));
		} else {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
	}
}