package com.at.reflect.model.response;

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
public class UserResponse {

	private Integer id;
	private String created;
	private String email;
	private String last_updated;
	private String name;
	private String password;

}