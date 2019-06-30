package com.uam.predictionapp.repository;

import com.uam.predictionapp.model.entity.AddonPredictionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
public interface AddonPredictionRepository extends CrudRepository<AddonPredictionEntity, Integer> {
    @Modifying
    @Query("UPDATE AddonPredictionEntity AS a SET a.HRG=:hrg, a.HRGDate=:currentDate WHERE a.userEntity.id=:userId")
    void updateHRG(@Param("hrg") String hrg, @Param("currentDate") Date currentDate, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE AddonPredictionEntity AS a SET a.HWT=:hwt, a.HWTDate=:currentDate WHERE a.userEntity.id=:userId")
    void updateHWT(@Param("hwt") String hwt, @Param("currentDate") Date currentDate, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE AddonPredictionEntity AS a SET a.POT=:pot, a.POTDate=:currentDate WHERE a.userEntity.id=:userId")
    void updatePOT(@Param("pot") String pot, @Param("currentDate") Date currentDate, @Param("userId") Long userId);

    AddonPredictionEntity findByUserEntityId(Long id);
}
