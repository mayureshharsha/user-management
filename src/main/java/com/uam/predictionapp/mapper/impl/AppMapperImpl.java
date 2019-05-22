package com.uam.predictionapp.mapper.impl;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.model.entity.PredictionId;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppMapperImpl implements AppMapper {

    @Autowired
    UserRepository userRepository;

    @Override
    public PredictionEntity predictionDtoToEntity(PredictionDto predictionDto) {
        if (predictionDto == null) {
            return null;
        }
        PredictionEntity predictionEntity = new PredictionEntity();
        PredictionId predictionEntityId = new PredictionId();
        predictionEntityId.setMatchId(predictionDto.getMatchId());
        final Optional<UserEntity> userEntity = userRepository.findById(predictionDto.getUserId());
        if (!userEntity.isPresent()){
            return null;
        }
        predictionEntityId.setUserEntity(userEntity.get());
        predictionEntity.setId(predictionEntityId);
        predictionEntity.setHomeResult(predictionDto.getHomeResult());
        return predictionEntity;
    }

    @Override
    public PredictionDto predictionEntityToDto(PredictionEntity predictionEntity) {
        if (predictionEntity == null){
            return null;
        }
        PredictionDto predictionDto = new PredictionDto();
        predictionDto.setUserId(predictionEntity.getId().getUserEntity().getId());
        predictionDto.setHomeResult(predictionEntity.getHomeResult());
        predictionDto.setMatchId(predictionEntity.getId().getMatchId());
        return predictionDto;
    }
}
