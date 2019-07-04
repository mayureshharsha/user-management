package com.uam.predictionapp.service;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.JackPot;
import com.uam.predictionapp.model.Match;
import com.uam.predictionapp.model.dto.AddonPredictionDto;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.entity.AddonPredictionEntity;
import com.uam.predictionapp.model.entity.PredictionEntity;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.AddonPredictionRepository;
import com.uam.predictionapp.repository.PredictionRepository;
import com.uam.predictionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private AddonPredictionRepository addonPredictionRepository;

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
        if (!userEntity.isPresent()) {
            return null;
        }
        List<PredictionEntity> prediction = predictionRepository.findPredictionEntityById_MatchIdAndId_UserEntity(matchId, userEntity.get());
        if (prediction.size() < 1) {
            return null;
        }
        return appMapper.predictionEntityToDto(prediction.get(0));
    }

    public boolean create(PredictionDto predictionDto) {
        final long currentTime = getCurrentTime();
        final Optional<Match> match = matchService.getMatch(predictionDto.getMatchId());
        /*check for timeout*/
        if (match.isPresent()) {
            final long matchTime = match.get().getDateTime().toInstant().minus(amountToSubtract, ChronoUnit.HOURS).getEpochSecond();
            if (matchTime < currentTime) {
                return false;
            }
        }
        PredictionDto prediction = getPrediction(predictionDto.getUserId(), predictionDto.getMatchId());
        if (prediction != null) {
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

    private long getCurrentTime() {
        Instant instant = Instant.now();
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

    public List<JackPot> getJackpotWinners() {
        final List<Match> latestMatchPlayed = matchService.getLatestMatchPlayed(2);
        List<JackPot> jackpotWinners = new ArrayList<>();
        latestMatchPlayed.forEach(match -> {
            final List<PredictionEntity> winners = predictionRepository.getJackpotWinners(match.getMatchId(), match.getHomeResult().ordinal(), match.getTossResult().ordinal(), match.getMomResult());
            winners.forEach(winner -> {
                jackpotWinners.add(new JackPot(winner.getId().getUserEntity().getUsername(), match.getMatchId()));
            });
        });
        return jackpotWinners;
    }

    public void saveAddonPrediction(AddonPredictionDto addonPredictionDto) {
        final Long userId = addonPredictionDto.getUserId();
        final AddonPredictionEntity addonPredictionEntity = addonPredictionRepository.findByUserEntityId(userId);
        Date currentDate = new Date();
        if (addonPredictionEntity == null) {
            if(addonPredictionDto.getPOT() != null)
                addonPredictionDto.setPOTDate(currentDate);
            if(addonPredictionDto.getHRG() != null)
                addonPredictionDto.setHRGDate(currentDate);
            if(addonPredictionDto.getHWT() != null)
                addonPredictionDto.setHWTDate(currentDate);
            addonPredictionRepository.save(appMapper.addonPredictionDtoToEntity(addonPredictionDto));
        } else {
            final AddonPredictionEntity entity = addonPredictionEntity;


            if (entity.getHRG() == null && addonPredictionDto.getHRG() != null) {
                addonPredictionRepository.updateHRG(addonPredictionDto.getHRG(), currentDate, addonPredictionDto.getUserId());
            }
            if (entity.getHWT() == null && addonPredictionDto.getHWT() != null) {
                addonPredictionRepository.updateHWT(addonPredictionDto.getHWT(), currentDate, addonPredictionDto.getUserId());
            }
            if (entity.getPOT() == null && addonPredictionDto.getPOT() != null) {
                addonPredictionRepository.updatePOT(addonPredictionDto.getPOT(), currentDate, addonPredictionDto.getUserId());
            }
        }
    }

    public AddonPredictionDto getAddonPredictionByUser(Long userId) {
        return appMapper.addonPredictionEntityToDto(addonPredictionRepository.findByUserEntityId(userId));
    }
}
