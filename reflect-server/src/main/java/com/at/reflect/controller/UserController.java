//package com.at.reflect.controller;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.at.reflect.controller.service.UserService;
//import com.reflect.generated.tables.pojos.User;
//
///**
// *
// * @author at
// */
//@Controller
//@RequestMapping(value = "/api/v1/user")
//public class UserController {
//
//	@Autowired
//	private UserService userService;
//
//	@PostMapping(value = "/{userId}")
//	@ResponseBody
//	public ResponseEntity<?> addUsers(@PathVariable(required = false) String userId,
//			@RequestParam(required = true) final String userEmail,
//			@RequestParam(required = true) final String userPassword) throws Exception {
//		User user = userService.processUser(userEmail, userPassword, userId);
//		if (user != null) {
//			return ResponseEntity.ok(userService.createNewUser(userEmail, userPassword));
//		}
//		throw new Exception();
//	}
//
//	@GetMapping(value = "/{userId}")
//	@ResponseBody
//	public ResponseEntity<?> getUser(@PathVariable String userId,
//			@RequestParam(required = false, defaultValue = "") final String userEmail,
//			@RequestParam(required = false, defaultValue = "") final String userPassword) throws Exception {
//		User user = userService.processUser(userEmail, userPassword, userId);
//		if (user != null) {
//			return ResponseEntity.ok(user);
//		}
//		throw new Exception();
//	}
//
//	@PutMapping(value = "/{userId}")
//	@ResponseBody
//	public ResponseEntity<?> udpateUsers(@PathVariable String userId,
//			@RequestParam(required = false, defaultValue = "") final String userEmail,
//			@RequestParam(required = false, defaultValue = "") final String userPassword,
//			@RequestParam(required = false, defaultValue = "") final String updatedUserEmail,
//			@RequestParam(required = false, defaultValue = "") final String updatedUserPassword,
//			@RequestParam(required = false, defaultValue = "") final String updatedUserName) throws Exception {
//		User user = userService.processUser(userEmail, userPassword, userId);
//		if (user != null) {
//			return ResponseEntity
//					.ok(userService.updateExistingUser(updatedUserEmail, updatedUserPassword, updatedUserName, user));
//		}
//		throw new Exception();
//	}
//
////	TODO: Delete account
////	@DeleteMapping(value = "/user")
////	@ResponseBody
////	public ResponseEntity<User> removeUser(@RequestParam(required = true) final String userEmail,
////			@RequestParam(required = true) final String userPassword) {
////		User user = userService.validateReqParams(userEmail, userPassword);
////		if (user != null) {
////			return ResponseEntity.ok(user);
////		} else {
////			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
////		}
////	}
//
////	TODO: List all user accounts
////	 
////	Needed for mobile dev. Pass for now, will redo 
////	PASS-Reference: 001
////	
//	@GetMapping(value = "/users")
//	@ResponseBody
//	public ResponseEntity<?> logUsers(@RequestParam(required = true) final String userEmail,
//			@RequestParam(required = true) final String userPassword) throws Exception {
//		User user = userService.fetchUser(userEmail, userPassword, "");
//		if (user != null) {
//			return ResponseEntity.ok(userService.fetchAllUsers());
//		}
//		throw new Exception();
//	}
//}

package com.at.reflect.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.model.request.UserRequest;
import com.at.reflect.model.response.UserResponse;
import com.at.reflect.server.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Meditation API", description = "Rest API to interact with Mediation")
public class UserController {

	private final UserService userService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
		log.debug("Creating new user...");
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
	}

	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> fetchUser(@PathVariable String userId) throws NotFoundException {
		log.debug("Fetching user...");
		return ResponseEntity.ok(userService.fetchUserById(userId));
	}

	@Operation(summary = "Update mediation by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "The entire mediation including submediations will be replaced, make sure to include all the fields (even the ones you're not changing)."
					+ " Fields that's not beed included will be deleted.") })
	@PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void udpateUser(@PathVariable String userId, @RequestBody @Valid UserRequest userRequest)
			throws NotFoundException {
		log.debug("Updating user...");
		userService.updateUser(userId, userRequest);
	}

}
