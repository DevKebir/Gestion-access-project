package com.episen.membership.service;

import com.episen.membership.model.User;
import com.episen.membership.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void add(User user) {
		if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getEmail())) {
			throw new RuntimeException("Username or email is empty!");
		}

		User existingUser = userRepository.getUserByUsername(user.getUsername());

		if (existingUser != null) {
			throw new RuntimeException("The user already exists!");
		}

		userRepository.addUser(user);
	}

	public List<User> getAllUsers() {
		return userRepository.getAll();
	}

	public User getByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("Username is empty!");
		}

		User user = userRepository.getUserByUsername(username);

		if (user == null) {
			throw new RuntimeException("User not found!");
		}

		return user;
	}

	public void updateUser(User userToUpdate) {
		if (StringUtils.isEmpty(userToUpdate.getUsername())) {
			throw new RuntimeException("Username is empty!");
		}

		User existingUser = userRepository.getUserByUsername(userToUpdate.getUsername());

		if (existingUser == null) {
			throw new RuntimeException("User not found!");
		}

		userRepository.update(userToUpdate);
	}

	public void deleteUser(String username) {
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("Username is empty!");
		}

		User existingUser = userRepository.getUserByUsername(username);

		if (existingUser == null) {
			throw new RuntimeException("User not found!");
		}

		userRepository.delete(username);
	}
}
