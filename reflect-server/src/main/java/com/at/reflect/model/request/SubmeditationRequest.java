package com.at.reflect.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubmeditationRequest {
    private Integer id;
    private String meditationAudioadress;
    private String meditationPlayerAdress;
    private String name;
    private Integer parentMeditationId;
}
