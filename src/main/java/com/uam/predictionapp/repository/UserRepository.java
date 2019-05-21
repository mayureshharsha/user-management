package com.uam.predictionapp.repository;

import javax.transaction.Transactional;

import com.uam.predictionapp.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Long>{

}
