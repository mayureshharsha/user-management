package com.uam.predictionapp.repository;

import com.uam.predictionapp.contants.MatchPredict;
import com.uam.predictionapp.contants.TossPredict;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.model.entity.PredictionId;
import com.uam.predictionapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PredictionRepository extends CrudRepository<PredictionEntity, PredictionId> {
    List<PredictionEntity> findPredictionEntityById_MatchIdAndId_UserEntity(Long matchId, UserEntity userEntity);

    @Query(value = "SELECT * FROM prediction where user_Id=:userId", nativeQuery = true)
    List<PredictionEntity> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM prediction where match_id=:matchId AND" +
            " home_result=:homeResult AND toss_result=:tossResult AND" +
            "  mom_result=:momResult", nativeQuery = true)
    List<PredictionEntity> getJackpotWinners(@Param("matchId")Long matchId,
                                             @Param("homeResult")MatchPredict homeResult,
                                             @Param("tossResult") TossPredict tossResult,
                                             @Param("momResult")String momResult);
}
