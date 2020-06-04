package com.at.reflect.controller.service;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.at.reflect.common.utils.JsonUtil;
import com.at.reflect.model.email.util.EmailUtil;
import com.at.reflect.model.entity.User;
import com.at.reflect.model.repository.UserRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserService implements Service {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailUtil emailUtil;

	public String fetchAllUsers() {
		return JsonUtil.usersToJsonArray(userRepository.findAll()).toString();
	}

	public User fetchUser(final String username, final String password, final String id) {
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			return StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(u -> username.equals(u.getUsername()) && password.equals(u.getPassword())).findAny()
					.orElse(null);
		} else if (!StringUtils.isEmpty(id)) {
			return userRepository.findById(Integer.valueOf(id)).orElse(null);
		}
		return null;
	}

	public User fetchUserById(final String id) {
		return fetchUser("", "", id);
	}

	public User fetchUserByUsernameAndPassword(final String username, final String password) {
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			return StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(u -> username.equals(u.getUsername()) && password.equals(u.getPassword())).findAny()
					.orElse(null);
		}
		return null;
	}

	public User fetchUserBySecret(final String secret) {
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

	public String convertToResponse(final User user) {
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

	public User convertJsonToUser(final String userJsonString, final boolean useId) {
		if (useId) {
			final JsonObject jsonObjectFromString = JsonParser.parseString(userJsonString).getAsJsonObject();
			User user = fetchUser("", "", jsonObjectFromString.get("id").getAsString());
			return user;
		}
		return convertJsonToUser(userJsonString);
	}

	public User convertJsonToUser(final String userJsonString) {
		final JsonObject jsonObjectFromString = JsonParser.parseString(userJsonString).getAsJsonObject();
		User user = fetchUser(jsonObjectFromString.get("username").getAsString(),
				jsonObjectFromString.get("password").getAsString(), "");
		return user;
	}

	public User createUserFromJson(final String userJsonString) {
		final JsonObject jsonObjectFromString = JsonParser.parseString(userJsonString).getAsJsonObject();
		User user = createNewUser(jsonObjectFromString.get("username").getAsString(),
				jsonObjectFromString.get("password").getAsString(),
				jsonObjectFromString.get("emailAddress").getAsString());
		return user;
	}

	public User updateExistingUser(final String userJsonString, final User user) {
		final JsonObject jsonObjectFromString = JsonParser.parseString(userJsonString).getAsJsonObject();
		user.setName(jsonObjectFromString.get("username").getAsString());
		user.setUsername(jsonObjectFromString.get("username").getAsString());
		user.setPassword(jsonObjectFromString.get("password").getAsString());
		user.setEmail(jsonObjectFromString.get("emailAddress").getAsString());
		save(user);
		emailUtil.sendEmail(user.getEmail());
		return user;
	}

	@Override
	public ServiceType getType() {
		return ServiceType.USER;
	}

}
