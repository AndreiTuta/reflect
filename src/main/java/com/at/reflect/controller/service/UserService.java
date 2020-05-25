package com.at.reflect.controller.service;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.at.reflect.model.email.util.EmailUtil;
import com.at.reflect.model.entity.User;
import com.at.reflect.model.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserService implements Service {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailUtil emailUtil;

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

	public String convertToResponse(User user) {
		return user.toString();
	}

	/**
	 * @param username
	 * @param password
	 * @param emailAddress
	 */
	public User createNewUser(final String username, final String password, final String emailAddress) {
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setEmail(emailAddress);
		save(newUser);
		emailUtil.sendEmail(newUser.getEmail());
		return newUser;
	}

	/**
	 * @param newPassword
	 * @param newUserName
	 * @param user
	 */
	public User updateExistingUser(final String newPassword, String newUserName, User user) {
		user.setUsername(newUserName);
		user.setPassword(newPassword);
		save(user);
		return user;
	}

	@Override
	public ServiceType getType() {
		return ServiceType.USER;
	}

}
