package com.at.reflect.controller.service;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.at.reflect.dao.UserDao;
import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.error.exception.PathException;
import com.at.reflect.model.request.UserRequest;
import com.at.reflect.model.response.UserResponse;
import com.reflect.generated.tables.pojos.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserService implements Service {
	private final UserDao userDao;
	private final ModelMapper modelMapper;

	private UserResponse.UserResponseBuilder buildUserResponse(final User user) {
		return UserResponse.builder().id(user.getId()).created(user.getCreated().toString()).email(user.getEmail())
				.last_updated(user.getLastUdpated()).name(user.getName()).password(user.getPassword());
	}

	@Override
	public ServiceType getType() {
		return ServiceType.USER;
	}

	public UserResponse createUser(@Valid UserRequest userRequest) {
		final User user = modelMapper.map(userRequest, User.class);
		userDao.insert(user);
		return buildUserResponse(user).build();
	}

	public void updateUser(String userId, @Valid UserRequest userRequest) {
		// TODO Auto-generated method stub

	}

	public UserResponse fetchUserById(String userId) throws NotFoundException {
		try {
			int id = Integer.parseInt(userId);
			final User user = fetchUserById(id)
					.orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found"));
			return buildUserResponse(user).build();
		} catch (NumberFormatException e) {
			throw new PathException("meditationId on path must be an integer");
		}
	}

	public Optional<User> fetchUserById(final Integer id) {
		return Optional.ofNullable(userDao.findById(id));

	}
}
