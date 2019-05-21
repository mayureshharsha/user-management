package com.uam.predictionapp.service;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PredictionService {

    @Autowired
    PredictionRepository predictionRepository;

    @Autowired
    AppMapper appMapper;

    public List<PredictionDto> getAllPredictions() {
        List<PredictionEntity> predictionEntities = (List<PredictionEntity>) predictionRepository.findAll();
        List<PredictionDto> predictionDtos = new ArrayList<>();
        predictionEntities.forEach(predictionEntity -> {
            predictionDtos.add(appMapper.predictionEntityToDto(predictionEntity));
        });
        return predictionDtos;
    }

    public PredictionDto getPrediction(Long id) {
        Optional<PredictionEntity> prediction = predictionRepository.findById(id);
        return appMapper.predictionEntityToDto(prediction.get());
    }

    public void create(PredictionDto predictionDto) {
        predictionRepository.save(appMapper.predictionDtoToEntity(predictionDto));
    }

    public void update(PredictionDto predictionDto) {
        predictionRepository.save(appMapper.predictionDtoToEntity(predictionDto));
    }

    public void delete(Long id) {
        predictionRepository.deleteById(id);
    }
}
