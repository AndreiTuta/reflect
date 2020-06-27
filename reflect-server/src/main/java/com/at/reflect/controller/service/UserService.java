package com.at.reflect.controller.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.at.reflect.model.entity.model.user.User;
import com.at.reflect.model.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserService implements Service {
	@Autowired
	private UserRepository userRepository;

	public List<User> fetchAllUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public User fetchUser(final String userEmail, final String userPassword, final String id) {
		if (!StringUtils.isEmpty(userEmail) && !StringUtils.isEmpty(userPassword)) {
			return fetchUserByEmailAndPassword(userEmail, userPassword);
		} else if (!StringUtils.isEmpty(id)) {
			return userRepository.findById(Integer.valueOf(id)).orElse(null);
		}
		return null;
	}

	public User fetchUserById(final String id) {
		return fetchUser("", "", id);
	}

	public User fetchUserByEmailAndPassword(final String email, final String password) {
		if (!StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)) {
			return StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(u -> email.equals(u.getEmail()) && password.equals(u.getPassword())).findAny().orElse(null);
		}
		return null;
	}

	public User fetchUserBySecret(final String secret) {
		if (!StringUtils.isEmpty(secret)) {
			return StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(u -> secret.equals(u.getName())).findAny().orElse(null);
		}
		return null;
	}

	public User createNewUser(String userEmail, String userPassword) {
		User user = null;
		if (!StringUtils.isEmpty(userEmail) && !StringUtils.isEmpty(userPassword)) {
			user = fetchUserByEmailAndPassword(userEmail, userPassword);
			if (user == null) {
				user = new User();
				user.setPassword(userPassword);
				user.setEmail(userEmail);
				return createNewUser(user);
			}
		}
		return user;
	}

	public User createNewUser(final User newUser) {
		if (newUser != null) {
			newUser.setCreated(LocalDateTime.now().toString());
			return userRepository.save(newUser);
		}
		return null;
	}

	// TODO: Take a look
	// https://www.baeldung.com/spring-data-jpa-modifying-annotation
	public User updateExistingUser(final String updatedUserEmail, final String updatedUserPassword,
			final String updatedUserName, final User user) {
		if (user != null) {
			if (!StringUtils.isEmpty(updatedUserEmail)) {
				user.setEmail(updatedUserEmail);
			}
			if (!StringUtils.isEmpty(updatedUserPassword)) {
				user.setPassword(updatedUserPassword);
			}
			if (!StringUtils.isEmpty(updatedUserName)) {
				user.setName(updatedUserName);
			}
			user.setLast_updated(LocalDateTime.now().toString());
			userRepository.save(user);
		}
		return user;
	}

	@Override
	public ServiceType getType() {
		return ServiceType.USER;
	}

	/**
	 * @param userEmail
	 * @param userPassword
	 */
	public User validateReqParams(final String userEmail, final String userPassword) {
		if (!StringUtils.isEmpty(userEmail) && !StringUtils.isEmpty(userPassword)) {
			User user = fetchUser(userEmail, userPassword, "");
			return user;
		}
		return null;
	}
}
