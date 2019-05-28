package com.uam.predictionapp.repository;

import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.model.entity.PredictionId;
import com.uam.predictionapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PredictionRepository extends CrudRepository<PredictionEntity, PredictionId>{
    List<PredictionEntity> findPredictionEntityById_MatchIdAndId_UserEntity(Long matchId, UserEntity userEntity);

    @Query(value = "SELECT * FROM prediction where user_Id=:userId", nativeQuery = true)
    List<PredictionEntity> findAllByUserId(@Param("userId") Long userId);
}
