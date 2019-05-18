package com.uam.predictionapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uam.predictionapp.model.User;
import com.uam.predictionapp.service.UserService;

@RestController("${userManagement.baseUrl}")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		userService.create(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
	
	@PutMapping("/users")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		userService.update(user);
		return new ResponseEntity<User>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.delete(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
}
