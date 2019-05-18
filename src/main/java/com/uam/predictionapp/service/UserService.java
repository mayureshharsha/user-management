package com.uam.predictionapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uam.predictionapp.model.User;
import com.uam.predictionapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 
	 * @param user
	 */
	public void create(User user) {
		userRepository.save(user);
	}
	
	public void update(User user) {
		userRepository.save(user);
	}
	
	public User getUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}
	
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
}
