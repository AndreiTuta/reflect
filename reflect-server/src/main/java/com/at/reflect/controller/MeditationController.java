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
import com.at.reflect.model.request.MeditationRequest;
import com.at.reflect.model.response.MeditationResponse;
import com.at.reflect.service.MeditationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/meditation")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Meditation API", description = "Rest API to interact with Mediation")
public class MeditationController {

    private final MeditationService meditationService;

    @Operation(summary = "Add mediation")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeditationResponse> addMeditation(@Valid @RequestBody MeditationRequest meditationRequest) {
        log.debug("Creating new meditation...");
        return ResponseEntity.status(HttpStatus.CREATED).body(meditationService.createMeditation(meditationRequest));
    }

    @Operation(summary = "Fetch mediation by ID")
    @GetMapping(value = "/{meditationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeditationResponse> fetchMeditation(@PathVariable String meditationId)
                                                                                                 throws NotFoundException {
        log.debug("Fetching meditation...");
        return ResponseEntity.ok(meditationService.fetchMeditationById(meditationId));
    }

    @Operation(summary = "Update mediation by ID")
    @PutMapping(value = "/{meditationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void udpateMeditation(@PathVariable String meditationId,
                                 @RequestBody @Valid MeditationRequest meditationRequest) throws NotFoundException {
        log.debug("Updating meditation...");
        meditationService.updateMeditation(meditationId, meditationRequest);
    }

}
