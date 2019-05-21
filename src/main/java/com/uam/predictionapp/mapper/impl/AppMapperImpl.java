package com.uam.predictionapp.mapper.impl;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        predictionEntity.setUserEntity(userRepository.findById(predictionDto.getUser_id()).get());
        predictionEntity.setMatchId(predictionDto.getMatchId());
        predictionEntity.setHomeResult(predictionDto.getHomeResult());
        predictionEntity.setId(predictionDto.getId());
        return predictionEntity;
    }

    @Override
    public PredictionDto predictionEntityToDto(PredictionEntity predictionEntity) {
        if (predictionEntity == null){
            return null;
        }
        PredictionDto predictionDto = new PredictionDto();
        predictionDto.setUser_id(predictionEntity.getUserEntity().getId());
        predictionDto.setHomeResult(predictionEntity.getHomeResult());
        predictionDto.setMatchId(predictionEntity.getMatchId());
        predictionDto.setId(predictionEntity.getId());
        return predictionDto;
    }
}
