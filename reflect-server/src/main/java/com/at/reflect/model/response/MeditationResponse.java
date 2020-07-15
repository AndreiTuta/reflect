package com.at.reflect.model.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class MeditationResponse {

    private Integer id;
    private Boolean available;
    private String duration;
    private String name;
    private String address;
    private Integer numMed;
    private String preview;
    @Builder.Default
    private List<SubmeditationResponse> submeditations = new ArrayList<>();
}
