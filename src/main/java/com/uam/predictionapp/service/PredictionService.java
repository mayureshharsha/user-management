package com.uam.predictionapp.service;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.model.entity.PredictionId;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.PredictionRepository;
import com.uam.predictionapp.repository.UserRepository;
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
    UserRepository userRepository;

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

    public PredictionDto getPrediction(Long userId, Long matchId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(!userEntity.isPresent()){
            return null;
        }
        List<PredictionEntity> prediction = predictionRepository.findPredictionEntityById_MatchIdAndId_UserEntity(matchId, userEntity.get());
        if(prediction.size() < 1){
            return null;
        }
        return appMapper.predictionEntityToDto(prediction.get(0));
    }

    public boolean create(PredictionDto predictionDto) {
        PredictionDto prediction = getPrediction(predictionDto.getUserId(), predictionDto.getMatchId());
        if(prediction != null){
            return false;
        }
        predictionRepository.save(appMapper.predictionDtoToEntity(predictionDto));
        return true;
    }

    public void update(PredictionDto predictionDto) {
        predictionRepository.save(appMapper.predictionDtoToEntity(predictionDto));
    }

    public void delete(Long id) {
        predictionRepository.deleteById(null);
    }
}
