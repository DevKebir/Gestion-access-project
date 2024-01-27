package com.episen.membership.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.episen.membership.model.User;
import com.episen.membership.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserResource {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody User user) {
		try {
			userService.add(user);
			return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to add user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		try {
			User user = userService.getByUsername(username);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{username}")
	public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
		try {
			updatedUser.setUsername(username);
			userService.updateUser(updatedUser);
			return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable String username) {
		try {
			userService.deleteUser(username);
			return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
