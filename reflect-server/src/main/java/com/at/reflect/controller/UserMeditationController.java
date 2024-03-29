package com.at.reflect.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.model.request.UserMeditationRequest;
import com.at.reflect.model.response.UserMeditationResponse;
import com.at.reflect.service.MeditationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/userMeditation")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "UserMeditation API", description = "Rest API to interact with UserMediation")
public class UserMeditationController {

    private final MeditationService meditationService;

    @Operation(summary = "Insert user mediation")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeditationResponse> addUserMeditation(@Valid @RequestBody UserMeditationRequest userMeditationRequest) {
        log.debug("Creating new meditation...");
        return ResponseEntity.status(HttpStatus.CREATED).body(meditationService.createUserMeditation(userMeditationRequest));
    }

    @Operation(summary = "Fetch user-mediation information by IDs")
    @GetMapping(value = "/{userId}/{userMeditationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeditationResponse> fetchUserMeditation(@PathVariable String userMeditationId, @PathVariable String userId)
                                                                                                                                          throws NotFoundException {
        log.debug("Fetching user meditation...");
        return ResponseEntity.ok(meditationService.fetchUserMeditationById(userMeditationId, userId));
    }

    @Operation(summary = "Update userMeditation by IDs")
    @PutMapping(value = "/{userId}/{userMeditationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeditationResponse> udpateuserMeditation(@PathVariable String userMeditationId, @PathVariable String userId,
                                                                       @RequestBody @Valid UserMeditationRequest userMeditationRequest)
                                                                                                                                        throws NotFoundException {
        log.debug("Updating userMeditation...");

        return ResponseEntity.ok(meditationService.updateUserMeditation(userMeditationId, userId, userMeditationRequest));
    }

    @Operation(summary = "Delete userMeditation by IDs")
    @DeleteMapping(value = "/{userId}/{userMeditationId}", consumes = MediaType.APPLICATION_JSON_VALUE,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeditationResponse> del(@PathVariable String userMeditationId, @PathVariable String userId)
                                                                                                                          throws NotFoundException {
        log.debug("Updating userMeditation...");
        return ResponseEntity.status(HttpStatus.GONE).body(meditationService.delete(userId, userMeditationId));
    }

}
