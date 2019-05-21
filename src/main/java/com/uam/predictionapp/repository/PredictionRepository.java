package com.uam.predictionapp.repository;

import com.uam.predictionapp.model.entity.PredictionEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface PredictionRepository extends CrudRepository<PredictionEntity, Long>{

}
