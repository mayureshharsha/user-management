package com.uam.predictionapp.service;

import com.uam.predictionapp.mapper.AppMapper;
import com.uam.predictionapp.model.Match;
import com.uam.predictionapp.model.dto.AddonPredictionDto;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.model.dto.ResultDto;
import com.uam.predictionapp.model.entity.RankingEntity;
import com.uam.predictionapp.model.entity.ResultEntity;
import com.uam.predictionapp.model.entity.UserEntity;
import com.uam.predictionapp.repository.PredictionRepository;
import com.uam.predictionapp.repository.RankingRepository;
import com.uam.predictionapp.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.uam.predictionapp.contants.AppConstants.INITIAL_POINTS;

@Service
public class ResultService {

    private AddonPredictionDto addPredictionFinalResult;

    private final long HRG_POINTS;

    private final long HWT_POINTS;

    private final long POT_POINTS;

    private final long PLAYER_BONUS_POINTS;

    private final long MATCH_FINALS_WIN_POINTS;

    private final long MATCH_FINALS_TOSS_WIN_POINTS;

    private final long MATCH_FINALS_MOM_WIN_POINTS;

    private final ResultRepository resultRepository;

    private final MatchService matchService;

    private final PredictionService predictionService;

    private final UserService userService;

    private final PredictionRepository predictionRepository;

    private final RankingRepository rankingRepository;

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

    private final long BONUS_POINTS;

    private final long SEMIS_BONUS_POINTS;

    private final long FINALS_BONUS_POINTS;

    private final boolean validateTimeCheck;

    private final long MATCH_SEMIS_WIN_POINTS;

    private final long MATCH_SEMIS_TOSS_WIN_POINTS;

    private final long MATCH_SEMIS_MOM_WIN_POINTS;

    public ResultService(@Autowired ResultRepository resultRepository, @Autowired MatchService matchService,
                         @Autowired PredictionService predictionService,
                         @Autowired UserService userService,
                         @Autowired PredictionRepository predictionRepository,
                         @Autowired AppMapper appMapper,
                         @Autowired RankingRepository rankingRepository,
                         @Value("${game.match.winPoints}") long matchWinPoints,
                         @Value("${game.match.losePoints}") long matchLosePoints,
                         @Value("${game.match.noPredictPoints}") long matchNoPredictPoints,
                         @Value("${game.toss.winPoints}") long tossWinPoints,
                         @Value("${game.toss.losePoints}") long tossLosePoints,
                         @Value("${game.toss.noPredictPoints}") long tossNoPredictPoints,
                         @Value("${game.mom.winPoints}") long momWinPoints,
                         @Value("${game.mom.losePoints}") long momLosePoints,
                         @Value("${game.mom.noPredictPoints}") long momNoPredictPoints,
                         @Value("${game.semisMatch.winPoints}") long semisWinPoints,
                         @Value("${game.finalsMatch.winPoints}") long finalsWinPoints,
                         @Value("${game.bonusPoints}") long bonusPoints,
                         @Value("${game.semisMatch.bonusPoints}") long semisBonusPoints,
                         @Value("${game.finalsMatch.bonusPoints}") long finalsBonusPoints,
                         @Value("${game.players.pot}") long potPoints,
                         @Value("${game.players.hrg}") long hrgPoints,
                         @Value("${game.players.hwt}") long hwtPoints,
                         @Value("${game.players.bonus}") long playerBonusPoints,
                         @Value("${game.semisToss.winPoints}") long semisTossWinPoints,
                         @Value("${game.semisMom.winPoints}") long semisMomWinPoints,
                         @Value("${game.finalsToss.winPoints}") long finalsTossWinPoints,
                         @Value("${game.finalsMom.winPoints}") long finalsMomWinPoints,
                         @Value("${validateTimeCheck}") boolean validateTimeCheck) {
        this.resultRepository = resultRepository;
        this.matchService = matchService;
        this.predictionService = predictionService;
        this.userService = userService;
        this.appMapper = appMapper;
        this.rankingRepository = rankingRepository;

        MATCH_WIN_POINTS = matchWinPoints;
        MATCH_LOSE_POINTS = matchLosePoints;
        MATCH_NO_PREDICT_POINTS = matchNoPredictPoints;

        TOSS_WIN_POINTS = tossWinPoints;
        TOSS_LOSE_POINTS = tossLosePoints;
        TOSS_NO_PREDICT_POINTS = tossNoPredictPoints;

        MOM_WIN_POINTS = momWinPoints;
        MOM_LOSE_POINTS = momLosePoints;
        MOM_NO_PREDICT_POINTS = momNoPredictPoints;

        BONUS_POINTS = bonusPoints;
        SEMIS_BONUS_POINTS = semisBonusPoints;
        FINALS_BONUS_POINTS = finalsBonusPoints;

        this.validateTimeCheck = validateTimeCheck;
        this.predictionRepository = predictionRepository;

        MATCH_SEMIS_WIN_POINTS = semisWinPoints;
        MATCH_SEMIS_MOM_WIN_POINTS = semisMomWinPoints;
        MATCH_SEMIS_TOSS_WIN_POINTS = semisTossWinPoints;

        MATCH_FINALS_WIN_POINTS = finalsWinPoints;
        MATCH_FINALS_TOSS_WIN_POINTS = finalsTossWinPoints;
        MATCH_FINALS_MOM_WIN_POINTS = finalsMomWinPoints;


        POT_POINTS = potPoints;
        HRG_POINTS = hrgPoints;
        HWT_POINTS = hwtPoints;
        PLAYER_BONUS_POINTS = playerBonusPoints;
    }

    public List<ResultDto> listResults() {
        return rankingRepository.getResults();
    }

    @Scheduled(cron = "${updateResult.cron}")
    public void calculate() {
        final List<Match> matches = matchService.loadMatches();
        final List<UserEntity> users = userService.getAllUsers();
        clearAllPoints();
        users.parallelStream().forEach(user -> matches.stream().forEach(match -> {
            Long userId = user.getId();
            Optional<ResultEntity> resultEntityOptional = resultRepository.findByUserId(userId);
            ResultEntity resultEntity;
            if (resultEntityOptional.isPresent()) {
                resultEntity = resultEntityOptional.get();
            } else {
                return;
            }
            Long existingPoints = resultEntity.getPoints();
            if (match.getHomeResult() != null && isValidTime(match)) {
                Long finalPoints = evaluatePoints(existingPoints, match, userId);
                resultEntity.setPoints(finalPoints);
                resultRepository.save(resultEntity);
            }
        }));
        calculateRanks();
    }

    private void calculateRanks() {
        ArrayList<RankingEntity> rankingEntities = (ArrayList<RankingEntity>) rankingRepository.findAll();
        if (rankingEntities.isEmpty()) {
            rankingRepository.setCurrentRank();
            rankingEntities = (ArrayList<RankingEntity>) rankingRepository.findAll();
        }
        final Date previousDate = rankingEntities.get(0).getPreviousDate();
        final Match latestMatchPlayed = matchService.getLatestMatchPlayed(1).get(0);
        Date latestMatchPlayedDateTime = latestMatchPlayed.getDateTime();
        System.out.println("latestMatchPlayedDateTime : " + latestMatchPlayedDateTime.toString());
        if ((previousDate == null) ||
                latestMatchPlayedDateTime.toInstant().getEpochSecond() > previousDate.toInstant().getEpochSecond()) {
            rankingRepository.resetPreviousDateRank(latestMatchPlayedDateTime);
            rankingRepository.setCurrentRank();
        }
    }

    private boolean isValidTime(Match match) {
        if (!validateTimeCheck) {
            return true;
        }
        Instant currentInstant = Instant.now();
        return match.getDateTime().toInstant().getEpochSecond() < currentInstant.getEpochSecond();
    }

    private long evaluatePoints(Long existingPoints, Match match, Long userId) {
        PredictionDto predictionDto = predictionService.getPrediction(userId, match.getMatchId());
        /*Penalise for not predicting*/
        if (predictionDto == null || predictionDto.getHomeResult() == null) {
            predictionRepository.save(appMapper.predictionDtoToEntity(
                    PredictionDto.builder().
                            matchId(match.getMatchId()).userId(userId).homeResult(null).tossResult(null).
                            build()));
            return existingPoints + this.MATCH_NO_PREDICT_POINTS + this.TOSS_NO_PREDICT_POINTS + this.MOM_LOSE_POINTS;
        }
        Long updatedPoints = evaluatePointsForMatch(predictionDto, match);
        updatedPoints += evaluatePointsForToss(predictionDto, match);
        updatedPoints += evaluatePointsForMom(predictionDto, match);

        if (match.getMatchId().equals(46l) || match.getMatchId().equals(47l)) {
            if (updatedPoints.equals(MATCH_SEMIS_WIN_POINTS + MATCH_SEMIS_TOSS_WIN_POINTS + MATCH_SEMIS_MOM_WIN_POINTS)) {
                updatedPoints += SEMIS_BONUS_POINTS;
            }
        } else if (match.getMatchId().equals(48l)) {
            if (updatedPoints.equals(MATCH_FINALS_WIN_POINTS + MATCH_FINALS_TOSS_WIN_POINTS + MATCH_FINALS_MOM_WIN_POINTS)) {
                updatedPoints += FINALS_BONUS_POINTS;
            }
            updatedPoints += evaluatePointsForPlayerPrediction(predictionDto.getUserId(), match);
        } else {
            if (updatedPoints.equals(MATCH_WIN_POINTS + TOSS_WIN_POINTS + MOM_WIN_POINTS)) {
                updatedPoints += BONUS_POINTS;
            }
        }
        return updatedPoints + existingPoints;
    }

    private Long evaluatePointsForPlayerPrediction(Long userId, Match finalMatch) {
        final AddonPredictionDto addonPredictionByUser = predictionService.getAddonPredictionByUser(userId);
        if (addonPredictionByUser == null)
            return 0l;
        final Date finalMatchDate = finalMatch.getDateTime();
        Long points = 0l;
        int counter = 0;
        if ((addPredictionFinalResult.getPOT()).equals(addonPredictionByUser.getPOT())) {
            final long daysDiff = ChronoUnit.DAYS.between(addonPredictionByUser.getPOTDate().toInstant(), finalMatchDate.toInstant()) + 1;
            points += daysDiff * POT_POINTS;
            counter++;
        }
        if (addPredictionFinalResult.getHWT().equals(addonPredictionByUser.getHWT())) {
            final long daysDiff = ChronoUnit.DAYS.between(addonPredictionByUser.getHWTDate().toInstant(), finalMatchDate.toInstant()) + 1;
            points += daysDiff * HWT_POINTS;
            counter++;
        }
        if (addPredictionFinalResult.getHRG().equals(addonPredictionByUser.getHRG())) {
            final long daysDiff = ChronoUnit.DAYS.between(addonPredictionByUser.getHRGDate().toInstant(), finalMatchDate.toInstant()) + 1;
            points += daysDiff * HRG_POINTS;
            counter++;
        }
        if(counter == 3){
            points += PLAYER_BONUS_POINTS;
        }

        return points;
    }

    private long evaluatePointsForMatch(PredictionDto predictionDto, Match match) {
        if (match.getHomeResult() == null) {
            return 0;
        }
        if (predictionDto.getHomeResult() == null) {
            return MATCH_NO_PREDICT_POINTS;
        } else {
            if (match.getMatchId().equals(46l) || match.getMatchId().equals(47l))
                return predictionDto.getHomeResult().equals(match.getHomeResult()) ? MATCH_SEMIS_WIN_POINTS : MATCH_LOSE_POINTS;
            else if (match.getMatchId().equals(48l))
                return predictionDto.getHomeResult().equals(match.getHomeResult()) ? MATCH_FINALS_WIN_POINTS : MATCH_LOSE_POINTS;
            else
                return predictionDto.getHomeResult().equals(match.getHomeResult()) ? MATCH_WIN_POINTS : MATCH_LOSE_POINTS;
        }
    }

    private long evaluatePointsForToss(PredictionDto predictionDto, Match match) {
        if (match.getTossResult() == null) {
            return 0;
        }
        if (predictionDto.getTossResult() == null) {
            return TOSS_NO_PREDICT_POINTS;
        } else {
            if (match.getMatchId().equals(46l) || match.getMatchId().equals(47l))
                return predictionDto.getTossResult().equals(match.getTossResult()) ? MATCH_SEMIS_TOSS_WIN_POINTS : TOSS_LOSE_POINTS;
            else if (match.getMatchId().equals(48l))
                return predictionDto.getTossResult().equals(match.getTossResult()) ? MATCH_FINALS_TOSS_WIN_POINTS : TOSS_LOSE_POINTS;
            else
                return predictionDto.getTossResult().equals(match.getTossResult()) ? TOSS_WIN_POINTS : TOSS_LOSE_POINTS;
        }
    }

    private long evaluatePointsForMom(PredictionDto predictionDto, Match match) {
        if (match.getMomResult() == null) {
            return 0;
        }
        if (predictionDto.getMomResult() == null) {
            return MOM_NO_PREDICT_POINTS;
        } else {
            if (match.getMatchId().equals(46l) || match.getMatchId().equals(47l))
                return predictionDto.getMomResult().equalsIgnoreCase(match.getMomResult()) ? MATCH_SEMIS_MOM_WIN_POINTS : MOM_LOSE_POINTS;
            else if (match.getMatchId().equals(48l))
                return predictionDto.getMomResult().equalsIgnoreCase(match.getMomResult()) ? MATCH_FINALS_MOM_WIN_POINTS : MOM_LOSE_POINTS;
            else
                return predictionDto.getMomResult().equalsIgnoreCase(match.getMomResult()) ? MOM_WIN_POINTS : MOM_LOSE_POINTS;
        }
    }

    private void clearAllPoints() {
        this.addPredictionFinalResult = predictionService.getAddonPredictionByUser(1l);
        resultRepository.setPoints(INITIAL_POINTS);
    }

    public Long getPoint(Long userId) {
        final Optional<ResultEntity> resultEntity = resultRepository.findByUserId(userId);
        return resultEntity.get().getPoints();
    }
}
