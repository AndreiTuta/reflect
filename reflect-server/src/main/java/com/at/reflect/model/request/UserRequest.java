package com.at.reflect.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private Integer id;
    private String created;
    @NotBlank(message = "User email is required")
    private String email;
    private String last_updated;
    @NotBlank(message = "User name is required")
    private String name;
    @NotBlank(message = "User password is required")
    private String password;
}