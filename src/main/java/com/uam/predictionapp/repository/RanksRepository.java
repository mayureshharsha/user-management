package com.uam.predictionapp.repository;

import com.uam.predictionapp.model.entity.RanksEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.uam.predictionapp.contants.RankStatus;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
public interface RanksRepository extends CrudRepository<RanksEntity, String> {

    @Modifying
    @Query(value = "UPDATE RanksEntity r SET r.rankStatus = CASE\n" +
            "WHEN r.currentRank > r.previousRank THEN 'I'\n" +
            "WHEN r.currentRank < r.previousRank THEN 'D'\n" +
            "ELSE 'S' END")
    void updateRankStatus();

    @Modifying
    @Query(value = "UPDATE RanksEntity r SET r.previousDate = :currentDate")
    void resetPreviousDate(@Param("currentDate")Date currentDate);

    @Modifying
    @Query(value = "update predictiondb.ranks set current_rank = \n" +
            "(select count(ranking.points) as rank\n" +
            "from predictiondb.result, (select distinct points from predictiondb.result) ranking, predictiondb.user\n" +
            "where ranking.points>=predictiondb.result.points and predictiondb.result.user_id = predictiondb.user.id\n" +
            "group by predictiondb.result.id\n" +
            "order by predictiondb.result.points desc)\n" +
            "where predictiondb.user.username = predictiondb.ranks.username", nativeQuery = true)
    void setCurrentRank();

    @Modifying
    @Query(value = "UPDATE RanksEntity r SET r.previousRank = r.currentRank")
    void setPreviousRank();
}
