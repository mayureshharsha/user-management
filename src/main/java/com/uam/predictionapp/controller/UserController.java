package com.uam.predictionapp.controller;

import java.util.List;

import com.uam.predictionapp.model.entity.UserEntity;
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

import com.uam.predictionapp.service.UserService;

@RestController("${userManagement.baseUrl}")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		List<UserEntity> userEntities = userService.getAllUsers();
		return new ResponseEntity<List<UserEntity>>(userEntities, HttpStatus.OK);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		return new ResponseEntity<UserEntity>(userService.getUser(id), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {
		userService.create(userEntity);
		return new ResponseEntity<UserEntity>(HttpStatus.CREATED);
	}
	
	@PutMapping("/users")
	public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity) {
		userService.update(userEntity);
		return new ResponseEntity<UserEntity>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.delete(id);
		return new ResponseEntity<UserEntity>(HttpStatus.NO_CONTENT);
	}
}
