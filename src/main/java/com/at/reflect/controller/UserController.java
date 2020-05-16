package com.at.reflect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.at.reflect.controller.service.UserService;
import com.at.reflect.model.email.util.EmailUtil;
import com.at.reflect.model.entity.User;

@Controller
@RequestMapping(value = "/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private EmailUtil emailUtil;
	// Constants
	private final String INVALID_CRED = "No params passed. Nothing has been executed";

	@PostMapping(value = "/addUser")
	@ResponseBody
	public ResponseEntity<String> addUsers(@RequestParam final String username, @RequestParam final String password,
			@RequestParam final String emailAddress) {
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			User user = userService.fetchUser(username, password, "");
			if (user != null) {
//				emailUtil.sendEmail(emailAddress);
			} else {
				User newUser = new User();
				newUser.setUsername(username);
				newUser.setPassword(password);
				newUser.setEmail(emailAddress);
				userService.save(newUser);
//				emailUtil.sendEmail(emailAddress);
				return ResponseEntity.ok(converToResponse(user));
			}
		}
		return ResponseEntity.ok(INVALID_CRED);

	}

	@GetMapping(value = "/getUser")
	@ResponseBody
	public ResponseEntity<String> logUsers(@RequestParam final String username, @RequestParam final String password) {
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			User user = userService.fetchUser(username, password, "");
			if (user != null) {
				return ResponseEntity.ok(converToResponse(user));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	@PutMapping(value = "/updateUser")
	@ResponseBody
	public ResponseEntity<String> udpateUsers(@RequestParam final String id, @RequestParam final String newUsername,
			@RequestParam final String newPassword) {
		if (!StringUtils.isEmpty(id)) {
			User user = userService.fetchUser("", "", id);
			if (user != null) {
				user.setUsername(newPassword);
				user.setPassword(newPassword);
				userService.save(user);
				return ResponseEntity.ok(converToResponse(user));
			} else {
				return ResponseEntity.ok("Failed retrieving info for provided params");
			}
		}
		return ResponseEntity.ok(INVALID_CRED);
	}

	private String converToResponse(User user) {
		return user.toString();
	}

}
