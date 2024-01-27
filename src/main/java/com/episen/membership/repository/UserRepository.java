package com.episen.membership.repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.episen.membership.model.User;
import com.episen.membership.model.Role;

import java.util.List;

@Component
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

	@Transactional
	public void addUser(User user) {
		logger.info("Adding user: {}", user.getUsername());
		jdbcTemplate.update("INSERT INTO member (username, email) VALUES (?, ?)", user.getUsername(), user.getEmail());

		Integer userId = jdbcTemplate.queryForObject("SELECT id FROM member WHERE username = ?", Integer.class, user.getUsername());

		for (Role role : user.getRoles()) {
			jdbcTemplate.update("INSERT INTO member_access (user_id, role_id) VALUES (?, ?)", userId, role.getId());
		}
	}

	public User getUserByUsername(String username) {
		logger.info("Getting user by username: {}", username);
		String sql = "SELECT * FROM member WHERE username = ?";
		try {
			User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);

			if (user != null) {
				// Retrieve roles for the user
				String rolesSql = "SELECT r.* FROM access r JOIN member_access ur ON r.id = ur.role_id WHERE ur.user_id = ?";
				user.setRoles(jdbcTemplate.query(rolesSql, new BeanPropertyRowMapper<>(Role.class), user.getId()));
			}

			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Transactional
	public void update(User userToUpdate) {
		logger.info("Updating user: {}", userToUpdate.getUsername());

		// Update user table
		String updateUserSql = "UPDATE member SET username = ?, email = ? WHERE id = ?";
		jdbcTemplate.update(updateUserSql, userToUpdate.getUsername(), userToUpdate.getEmail(), userToUpdate.getId());

		// Remove existing roles for the user
		String deleteRolesSql = "DELETE FROM member_access WHERE user_id = ?";
		jdbcTemplate.update(deleteRolesSql, userToUpdate.getId());

		// Add updated roles for the user
		for (Role role : userToUpdate.getRoles()) {
			String insertRoleSql = "INSERT INTO member_access (user_id, role_id) VALUES (?, ?)";
			jdbcTemplate.update(insertRoleSql, userToUpdate.getId(), role.getId());
		}
	}

	@Transactional
	public void delete(String username) {
		logger.info("Deleting user by username: {}", username);
		String getUserIdSql = "SELECT id FROM member WHERE username = ?";
		Integer userId = jdbcTemplate.queryForObject(getUserIdSql, Integer.class, username);

		if (userId != null) {
			// Delete user roles
			String deleteRolesSql = "DELETE FROM member_access WHERE user_id = ?";
			jdbcTemplate.update(deleteRolesSql, userId);

			// Delete user
			String deleteUserSql = "DELETE FROM member WHERE id = ?";
			jdbcTemplate.update(deleteUserSql, userId);
		}
	}

	@Transactional
	public List<User> getAll() {
		logger.info("Getting all users.");
		String sql = "SELECT * FROM member";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));

		for (User user : users) {
			// Retrieve roles for each user
			String rolesSql = "SELECT r.* FROM access r JOIN member_access ur ON r.id = ur.role_id WHERE ur.user_id = ?";
			user.setRoles(jdbcTemplate.query(rolesSql, new BeanPropertyRowMapper<>(Role.class), user.getId()));
		}

		return users;
	}



}
