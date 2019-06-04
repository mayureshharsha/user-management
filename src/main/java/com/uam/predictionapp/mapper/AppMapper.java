package com.uam.predictionapp.mapper;

import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.dto.ResultDto;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.model.entity.PredictionId;
import com.uam.predictionapp.model.entity.ResultEntity;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class AppMapper {

    @Autowired
    UserRepository userRepository;


    public PredictionEntity predictionDtoToEntity(PredictionDto predictionDto) {
        if (predictionDto == null) {
            return null;
        }
        PredictionEntity predictionEntity = new PredictionEntity();
        PredictionId predictionEntityId = new PredictionId();
        predictionEntityId.setMatchId(predictionDto.getMatchId());
        final Optional<UserEntity> userEntity = userRepository.findById(predictionDto.getUserId());
        if (!userEntity.isPresent()) {
            return null;
        }
        predictionEntityId.setUserEntity(userEntity.get());
        predictionEntity.setId(predictionEntityId);
        predictionEntity.setHomeResult(predictionDto.getHomeResult());
        predictionEntity.setTossResult(predictionDto.getTossResult());
        predictionEntity.setMomResult(predictionDto.getMomResult());
        return predictionEntity;
    }

    public PredictionDto predictionEntityToDto(PredictionEntity predictionEntity) {
        if (predictionEntity == null) {
            return null;
        }
        PredictionDto predictionDto = new PredictionDto();
        predictionDto.setUserId(predictionEntity.getId().getUserEntity().getId());
        predictionDto.setHomeResult(predictionEntity.getHomeResult());
        predictionDto.setMatchId(predictionEntity.getId().getMatchId());
        predictionDto.setTossResult(predictionEntity.getTossResult());
        predictionDto.setMomResult(predictionEntity.getMomResult());
        return predictionDto;
    }

    @Mappings({
            @Mapping(target = "username", source = "user.username")
    })
    public abstract ResultDto resultEntityToDto(ResultEntity resultEntity);
}