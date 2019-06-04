package com.uam.predictionapp.controller;

import java.util.List;

import com.uam.predictionapp.model.TokenDto;
import com.uam.predictionapp.model.dto.UserDto;
import com.uam.predictionapp.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uam.predictionapp.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("${userMgmt.baseUrl}")
public class UserController {

	@Autowired
	UserService userService;
	
//	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		List<UserEntity> userEntities = userService.getAllUsers();
		return new ResponseEntity<>(userEntities, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody UserDto userDto) {
		final TokenDto token = userService.login(userDto);
		if(token != null)
			return new ResponseEntity<>(token, HttpStatus.OK);
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}
	
//	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody @Valid UserEntity userEntity) {
		boolean created = userService.create(userEntity);
		return created? new ResponseEntity<>(HttpStatus.CREATED): new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
//	@PutMapping("/users")
	public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity) {
		userService.update(userEntity);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
//	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
