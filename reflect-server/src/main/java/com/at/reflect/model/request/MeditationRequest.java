package com.at.reflect.model.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeditationRequest {
    @NotBlank(message = "Meditation name is required")
    private String name;
    @NotBlank(message = "Meditation duration is required")
    private String duration;
    @NotBlank(message = "Meditation address is required")
    private String address;
    @NotBlank(message = "Meditation num of meditation is required")
    private String numMed;
    @NotBlank(message = "preview is required")
    private String preview;
    @NotNull(message = "isAvaliable is required")
    private Boolean isAvailable;
    private List<SubmeditationRequest> submeditations;
}