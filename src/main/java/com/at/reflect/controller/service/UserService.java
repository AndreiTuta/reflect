package com.at.reflect.controller.service;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.at.reflect.model.entity.User;
import com.at.reflect.model.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserService implements Service {

	@Autowired
	private UserRepository userRepository;

	public User fetchUser(String username, String password, String id) {
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			return StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(u -> username.equals(u.getUsername()) && password.equals(u.getPassword())).findAny()
					.orElse(null);
		} else if (!StringUtils.isEmpty(id)) {
			return userRepository.findById(Integer.valueOf(id)).orElse(null);
		}
		return null;
	}

	public User fetchUserById(String id) {
		return fetchUser("", "", id);
	}

	public User fetchUserByUsernameAndPassword(String username, String password) {
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			return StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(u -> username.equals(u.getUsername()) && password.equals(u.getPassword())).findAny()
					.orElse(null);
		}
		return null;
	}

	public User fetchUserBySecret(String secret) {
		if (!StringUtils.isEmpty(secret)) {
			return StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(u -> secret.equals(u.getUsername())).findAny().orElse(null);
		}
		return null;
	}

	public boolean save(User newUser) {
		if (newUser != null) {
			userRepository.save(newUser);
			return true;
		}
		return false;
	}

	@Override
	public ServiceType getType() {
		return ServiceType.USER;
	}
}
