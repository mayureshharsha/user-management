package com.uam.predictionapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.uam.predictionapp.model.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>{

}
