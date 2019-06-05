package com.uam.predictionapp.repository;

import com.uam.predictionapp.model.entity.ResultEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Transactional
public interface ResultRepository extends CrudRepository<ResultEntity, Long> {
    @Modifying
    @Query("UPDATE ResultEntity r SET r.points=:point")
    void setPoints(@Param("point") Long point);

    //    @Query(value = "SELECT points FROM result WHERE userid=:userId", nativeQuery = true)
    Optional<ResultEntity> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "update result set current_rank = \n" +
            "(select count(ranking.points) as rank\n" +
            "from result, (select distinct points from result) ranking, user\n" +
            "where ranking.points>=result.points and result.user_id = user.id\n" +
            "group by result.id)", nativeQuery = true)
    void setCurrentRank();

    @Query(value = "SELECT * FROM result LIMIT 1", nativeQuery = true)
    Optional<ResultEntity> getOne();

    @Modifying
    @Query(value = "UPDATE ResultEntity r SET r.previousDate = :currentDate, r.previousRank = r.currentRank")
    void resetPreviousDate(@Param("currentDate") Date currentDate);

}