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
import com.at.reflect.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User API", description = "Rest API to interact with User")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Add a User")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        log.debug("Creating new user...");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @Operation(summary = "Fetch User by ID")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> fetchUser(@PathVariable String userId) throws NotFoundException {
        log.debug("Fetching user...");
        return ResponseEntity.ok(userService.fetchUserResponseById(userId));
    }

    @Operation(summary = "Update User by ID")
    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void udpateUser(@PathVariable String userId, @RequestBody @Valid UserRequest userRequest)
                                                                                                     throws NotFoundException {
        log.debug("Updating user...");
        userService.updateUser(userId, userRequest);
    }

}
