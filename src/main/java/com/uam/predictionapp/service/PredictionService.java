package com.uam.predictionapp.service;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.Match;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.PredictionRepository;
import com.uam.predictionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PredictionService {

    @Value("${predictionTimeLimit}")
    private final int amountToSubtract = 1;

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private MatchService matchService;

    public List<PredictionDto> getAllPredictions() {
        List<PredictionEntity> predictionEntities = (List<PredictionEntity>) predictionRepository.findAll();
        List<PredictionDto> predictionDtos = new ArrayList<>();
        predictionEntities.forEach(predictionEntity -> {
            predictionDtos.add(appMapper.predictionEntityToDto(predictionEntity));
        });
        return predictionDtos;
    }

    public PredictionDto getPrediction(Long userId, Long matchId) {
        /*Check if user is registered*/
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
        final long timeLimit = getTimeLimit();
        final Optional<Match> match = matchService.getMatch(predictionDto.getMatchId());
        /*check for timeout*/
        if(match.isPresent() && match.get().getDateTime().toInstant().getEpochSecond() < timeLimit){
            return false;
        }
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

    private long getTimeLimit() {
        Instant instant = Instant.now().minus(amountToSubtract, ChronoUnit.HOURS);
        return instant.getEpochSecond();
    }

    public List<PredictionDto> getPredictionsByUser(Long userId) {
        final List<PredictionEntity> predictionEntities = predictionRepository.findAllByUserId(userId);
        List<PredictionDto> predictionDtos = new ArrayList<>();
        matchService.loadMatches();
        predictionEntities.forEach(predictionEntity -> {
            PredictionDto predictionDto = appMapper.predictionEntityToDto(predictionEntity);
            predictionDto.setMatch(matchService.getMatch(predictionDto.getMatchId()).get());
            predictionDtos.add(predictionDto);
        });

        return predictionDtos;
    }
}
