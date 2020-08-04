package com.at.reflect.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailRequest {
    private Boolean available;
    @NotBlank(message = "At least 1 character is required")
    private String text;
    @NotBlank(message = "Email name (subject) is required")
    private String name;
    @NotBlank(message = "Sender address is required")
    private String address;
    private String preview;
}