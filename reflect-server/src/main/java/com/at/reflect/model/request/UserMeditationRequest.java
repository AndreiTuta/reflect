package com.at.reflect.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMeditationRequest {
    @NotBlank(message = "Meditation id is required")
    private Integer meditationId;
    @NotBlank(message = "User id is required")
    private Integer userId;
    private String userMeditationText;
    private Integer id;
}