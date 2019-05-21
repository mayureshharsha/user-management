package com.uam.predictionapp.service;

import java.util.List;
import java.util.Optional;

import com.uam.predictionapp.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uam.predictionapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 
	 * @param userEntity
	 */
	public void create(UserEntity userEntity) {
		userRepository.save(userEntity);
	}
	
	public void update(UserEntity userEntity) {
		userRepository.save(userEntity);
	}
	
	public UserEntity getUser(Long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		return user.get();
	}
	
	public List<UserEntity> getAllUsers(){
		return (List<UserEntity>) userRepository.findAll();
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
}
