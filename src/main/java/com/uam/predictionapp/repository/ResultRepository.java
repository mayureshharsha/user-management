package com.uam.predictionapp.repository;

import com.uam.predictionapp.model.entity.ResultEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ResultRepository extends CrudRepository<ResultEntity, Long> {
    @Modifying
    @Query("UPDATE ResultEntity r SET r.points=:point")
    void setPoints(@Param("point") Long point);

    //    @Query(value = "SELECT points FROM result WHERE userid=:userId", nativeQuery = true)
    Optional<ResultEntity> findByUserId(Long userId);
}
