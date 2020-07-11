package com.at.reflect.controller;

import org.apache.commons.lang3.StringUtils;
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

import com.at.reflect.controller.service.UserService;
import com.at.reflect.model.entity.user.User;

@Controller
@RequestMapping(value = "/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/")
	@ResponseBody
	public ResponseEntity<?> addUsers(@PathVariable String userId,
			@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String userPassword) throws Exception {
		User user = userService.processUser(userEmail, userPassword, userId);
		if (user != null) {
			return ResponseEntity.ok(userService.createNewUser(userEmail, userPassword));
		}
		throw new Exception();
	}

	@GetMapping(value = "/{userId}")
	@ResponseBody
	public ResponseEntity<?> getUser(@PathVariable String userId,
			@RequestParam(required = false, defaultValue = "") final String userEmail,
			@RequestParam(required = false, defaultValue = "") final String userPassword) throws Exception {
		User user = userService.processUser(userEmail, userPassword, userId);
		if (user != null) {
			return ResponseEntity.ok(user);
		}
		throw new Exception();
	}

	@PutMapping(value = "/{userId}")
	@ResponseBody
	public ResponseEntity<?> udpateUsers(@PathVariable String userId,
			@RequestParam(required = false, defaultValue = "") final String userEmail,
			@RequestParam(required = false, defaultValue = "") final String userPassword,
			@RequestParam(required = false, defaultValue = "") final String updatedUserEmail,
			@RequestParam(required = false, defaultValue = "") final String updatedUserPassword,
			@RequestParam(required = false, defaultValue = "") final String updatedUserName) throws Exception {
		User user = userService.processUser(userEmail, userPassword, userId);
		if (user != null) {
			return ResponseEntity
					.ok(userService.updateExistingUser(updatedUserEmail, updatedUserPassword, updatedUserName, user));
		}
		throw new Exception();
	}

//	TODO: Delete account
//	@DeleteMapping(value = "/user")
//	@ResponseBody
//	public ResponseEntity<User> removeUser(@RequestParam(required = true) final String userEmail,
//			@RequestParam(required = true) final String userPassword) {
//		User user = userService.validateReqParams(userEmail, userPassword);
//		if (user != null) {
//			return ResponseEntity.ok(user);
//		} else {
//			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//		}
//	}

//	TODO: List all user accounts
//	 
//	Needed for mobile dev. Pass for now, will redo 
//	PASS-Reference: 001
//	
	@GetMapping(value = "/users")
	@ResponseBody
	public ResponseEntity<?> logUsers(@RequestParam(required = true) final String userEmail,
			@RequestParam(required = true) final String userPassword) throws Exception {
		User user = userService.fetchUser(userEmail, userPassword, "");
		if (user != null) {
			return ResponseEntity.ok(userService.fetchAllUsers());
		}
		throw new Exception();
	}
}