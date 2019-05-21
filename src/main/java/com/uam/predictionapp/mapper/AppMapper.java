package com.uam.predictionapp.mapper;

import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.entity.PredictionEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AppMapper {
    PredictionEntity predictionDtoToEntity(PredictionDto predictionDto);
    PredictionDto predictionEntityToDto(PredictionEntity predictionEntity);
}