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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.model.request.EmailRequest;
import com.at.reflect.model.response.EmailResponse;
import com.at.reflect.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1/email")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Email API", description = "Rest API to interact with Email")
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "Add a Email")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailResponse> addEmail(@Valid @RequestBody EmailRequest emailRequest) {
        log.debug("Creating new email...");
        return ResponseEntity.status(HttpStatus.CREATED).body(emailService.createEmail(emailRequest));
    }

    @Operation(summary = "Fetch Email by ID")
    @GetMapping(value = "/{emailId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailResponse> fetchEmail(@PathVariable String emailId) throws NotFoundException {
        log.debug("Fetching email...");
        return ResponseEntity.ok(emailService.fetchEmailById(emailId));
    }

    @Operation(summary = "Update Email by ID")
    @PutMapping(value = "/{emailId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void udpateEmail(@PathVariable String emailId, @RequestBody @Valid EmailRequest emailRequest)
                                                                                                         throws NotFoundException {
        log.debug("Updating email...");
        emailService.updateEmail(emailId, emailRequest);
    }

    @Operation(summary = "Delete Email by ID")
    @DeleteMapping(value = "/{emailId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<EmailResponse> del(@PathVariable String emailId, @RequestBody @Valid EmailRequest emailRequest)
                                                                                                 throws NotFoundException {
        log.debug("Updating email...");
        emailService.updateEmail(emailId, emailRequest);
        return ResponseEntity.ok(emailService.delete(emailId));
    }

}
