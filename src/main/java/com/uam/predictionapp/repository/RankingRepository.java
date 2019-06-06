package com.uam.predictionapp.repository;

import com.uam.predictionapp.model.dto.ResultDto;
import com.uam.predictionapp.model.entity.RankingEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface RankingRepository extends CrudRepository<RankingEntity, Long> {

    @Modifying
    @Query(value = "insert into ranking(id, current_rank, username) \n" +
            "(select result.id, count(ranks.points) asrank, user.username\n" +
            "from result, (select distinct points from result) ranks, user\n" +
            "where ranks.points>=result.points and result.user_id = user.id\n" +
            "group by result.id)\n" +
            "on duplicate key update current_rank = values(current_rank)", nativeQuery = true)
    void setCurrentRank();

    @Modifying
    @Query(value = "UPDATE RankingEntity r SET r.previousDate = :currentDate, r.previousRank = r.currentRank")
    void resetPreviousDateRank(@Param("currentDate") Date currentDate);

    @Query("SELECT new com.uam.predictionapp.model.dto.ResultDto(r.username, rr.points, r.currentRank, r.previousRank) from RankingEntity r, ResultEntity rr where r.id = rr.id")
    List<ResultDto> getResults();
}
