package com.uam.predictionapp.controller;

import com.uam.predictionapp.model.JackPot;
import com.uam.predictionapp.model.dto.AddonPredictionDto;
import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("${predictionMgmt.baseUrl}")
public class PredictionController {

    @Autowired
    PredictionService predictionService;

    @GetMapping("/predictions/jackpotwinners")
    public ResponseEntity<?> getJackpotWinners() {
        List<JackPot> jackpotWinners = predictionService.getJackpotWinners();
        return new ResponseEntity<>(jackpotWinners, HttpStatus.OK);
    }

    @GetMapping("/predictions")
    public ResponseEntity<?> getAllPredictions() {
        List<PredictionDto> predictions = predictionService.getAllPredictions();
        return new ResponseEntity<>(predictions, HttpStatus.OK);
    }

    //    @GetMapping("/predictions/{userId}/{matchId}")
    public ResponseEntity<?> getPrediction(@PathVariable Long userId, @PathVariable Long matchId) {
        return new ResponseEntity<>(predictionService.getPrediction(userId, matchId), HttpStatus.OK);
    }

    @PostMapping("/predictions")
    public ResponseEntity<?> createPrediction(@RequestBody @Valid PredictionDto prediction) {
        final boolean result = predictionService.create(prediction);
        return result ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<PredictionDto>(HttpStatus.FORBIDDEN);
    }

    //    @PutMapping("/predictions")
    public ResponseEntity<?> updatePrediction(@RequestBody PredictionDto prediction) {
        predictionService.update(prediction);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    //    @DeleteMapping("/predictions/{id}")
    public ResponseEntity<?> deletePrediction(@PathVariable Long id) {
        predictionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/predictionsByUser/{userId}")
    public ResponseEntity<?> getPredictionsByUser(@PathVariable Long userId) {
        List<PredictionDto> predictionDtos = predictionService.getPredictionsByUser(userId);
        predictionDtos.sort(Comparator.comparingLong(PredictionDto::getMatchId).reversed());
        return new ResponseEntity<List<PredictionDto>>(predictionDtos, HttpStatus.OK);
    }

    @PostMapping("/predictions/addonPrediction")
    public ResponseEntity<?> createAddonPrediction(@RequestBody @Valid AddonPredictionDto addonPredictionDto) {
        predictionService.saveAddonPrediction(addonPredictionDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/predictions/addonPrediction/{userId}")
    public ResponseEntity<?> getAddonPrediction(@RequestParam("userId") Long userId) {
        final AddonPredictionDto addonPredictionByUser = predictionService.getAddonPredictionByUser(userId);
        return new ResponseEntity<>(addonPredictionByUser, HttpStatus.ACCEPTED);
    }
}
