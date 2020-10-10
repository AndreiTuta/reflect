package com.at.reflect.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMeditationRequest {
    private Integer meditationId;
    private Integer userId;
    private String userMeditationText;
}