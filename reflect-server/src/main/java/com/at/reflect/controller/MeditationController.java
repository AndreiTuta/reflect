package com.at.reflect.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.at.reflect.controller.service.MeditationService;
import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.model.request.MeditationRequest;
import com.at.reflect.model.response.MeditationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/api/v1/meditation")
@Slf4j
@RequiredArgsConstructor
public class MeditationController {

    private final MeditationService meditationService;

    @PostMapping
    public ResponseEntity<MeditationResponse> addMeditation(@Valid @RequestBody MeditationRequest meditationRequest) {
        log.debug("Creating new meditation...");
        return ResponseEntity.status(HttpStatus.CREATED).body(meditationService.createMeditation(meditationRequest));
    }

    @GetMapping(value = "/{meditationId}")
    public ResponseEntity<MeditationResponse> fetchMeditation(@PathVariable String meditationId) throws NotFoundException {
        log.debug("Fetching meditation...");
        return ResponseEntity.ok(meditationService.fetchMeditationById(meditationId));
    }

    @PutMapping(value = "/{meditationId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void udpateMeditation(@PathVariable String meditationId,
                                 @RequestBody MeditationRequest meditationRequest) throws NotFoundException {
        log.debug("Updating meditation...");
        meditationService.updateMeditation(meditationId, meditationRequest);
    }

}
