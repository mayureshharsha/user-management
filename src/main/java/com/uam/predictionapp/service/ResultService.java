package com.uam.predictionapp.service;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.Match;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.dto.ResultDto;
import com.uam.predictionapp.model.entity.ResultEntity;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    private final ResultRepository resultRepository;

    private final MatchService matchService;

    private final PredictionService predictionService;

    private final AppMapper appMapper;

    private final long MATCH_WIN_POINTS;

    private final long MATCH_LOSE_POINTS;

    private final long MATCH_NO_PREDICT_POINTS;

    private final long TOSS_LOSE_POINTS;

    private final long TOSS_WIN_POINTS;

    private final long TOSS_NO_PREDICT_POINTS;

    private final long MOM_LOSE_POINTS;

    private final long MOM_WIN_POINTS;

    private final long MOM_NO_PREDICT_POINTS;

    public ResultService(@Autowired ResultRepository resultRepository, @Autowired MatchService matchService,
                         @Autowired PredictionService predictionService,
                         @Autowired AppMapper appMapper,
                         @Value("${game.match.winPoints}") long matchWinPoints,
                         @Value("${game.match.losePoints}") long matchLosePoints,
                         @Value("${game.match.noPredictPoints}") long matchNoPredictPoints,
                         @Value("${game.toss.winPoints}") long tossWinPoints,
                         @Value("${game.toss.losePoints}") long tossLosePoints,
                         @Value("${game.toss.noPredictPoints}") long tossNoPredictPoints,
                         @Value("${game.mom.winPoints}") long momWinPoints,
                         @Value("${game.mom.losePoints}") long momLosePoints,
                         @Value("${game.mom.noPredictPoints}") long momNoPredictPoints
    ) {
        this.resultRepository = resultRepository;
        this.matchService = matchService;
        this.predictionService = predictionService;
        this.appMapper = appMapper;

        MATCH_WIN_POINTS = matchWinPoints;
        MATCH_LOSE_POINTS = matchLosePoints;
        MATCH_NO_PREDICT_POINTS = matchNoPredictPoints;

        TOSS_WIN_POINTS = tossWinPoints;
        TOSS_LOSE_POINTS = tossLosePoints;
        TOSS_NO_PREDICT_POINTS = tossNoPredictPoints;

        MOM_WIN_POINTS = momWinPoints;
        MOM_LOSE_POINTS = momLosePoints;
        MOM_NO_PREDICT_POINTS = momNoPredictPoints;
    }

    public List<ResultDto> listResults() {
        List<ResultEntity> resultEntities = (List<ResultEntity>) resultRepository.findAll();
        List<ResultDto> resultDtos = new ArrayList<>();
        resultEntities.forEach(resultEntity -> {
            resultDtos.add(appMapper.resultEntityToDto(resultEntity));
        });
        return resultDtos;
    }

    public void calculate() {
        List<PredictionDto> allPredictions = predictionService.getAllPredictions();
        clearAllPoints();
        matchService.loadMatches();
        allPredictions.parallelStream().forEach(predictionDto -> {
            Long matchId = predictionDto.getMatchId();
            Long userId = predictionDto.getUserId();
            Optional<ResultEntity> resultEntityOptional = resultRepository.findByUserId(userId);
            ResultEntity resultEntity = resultEntityOptional.isPresent() ? resultEntityOptional.get() : ResultEntity.builder().user(UserEntity.builder().id(userId).build()).points(0l).build();
            Long existingPoints = resultEntity.getPoints();
            Optional<Match> match = matchService.getMatch(matchId);
            if (match.isPresent() && match.get().getHomeResult() != null) {
                Long finalPoints = evaluatePoints(predictionDto, existingPoints, match.get());
                resultEntity.setPoints(finalPoints);
                resultRepository.save(resultEntity);
            }
        });

    }

    private long evaluatePoints(PredictionDto predictionDto, Long existingPoints, Match match) {
        Long updatedPoints = existingPoints;
        updatedPoints += evaluatePointsForMatch(predictionDto, match);
        updatedPoints += evaluatePointsForToss(predictionDto, match);
        updatedPoints += evaluatePointsForMom(predictionDto, match);
        return updatedPoints;
    }

    private long evaluatePointsForMatch(PredictionDto predictionDto, Match match) {
        if (match.getTossResult() == null) {
            return 0;
        }
        if (predictionDto.getHomeResult() == null) {
            return MATCH_NO_PREDICT_POINTS;
        } else {
            return predictionDto.getHomeResult().equals(match.getHomeResult()) ? MATCH_WIN_POINTS : MATCH_LOSE_POINTS;
        }
    }

    private long evaluatePointsForToss(PredictionDto predictionDto, Match match) {
        if (match.getMomResult() == null) {
            return 0;
        }
        if (predictionDto.getTossResult() == null) {
            return TOSS_NO_PREDICT_POINTS;
        } else {
            return predictionDto.getTossResult().equals(match.getTossResult()) ? TOSS_WIN_POINTS : TOSS_LOSE_POINTS;
        }
    }

    private long evaluatePointsForMom(PredictionDto predictionDto, Match match) {
        if (predictionDto.getMomResult() == null) {
            return MOM_NO_PREDICT_POINTS;
        } else {
            return predictionDto.getMomResult().equals(match.getMomResult()) ? MOM_WIN_POINTS : MOM_LOSE_POINTS;
        }
    }

    private void clearAllPoints() {
        resultRepository.setPoints(0l);
    }
}