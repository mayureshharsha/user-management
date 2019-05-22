package com.uam.predictionapp.controller;

import com.uam.predictionapp.model.dto.PredictionDto;
import com.uam.predictionapp.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("${predictionMgmt.baseUrl}")
public class PredictionController {

    @Autowired
    PredictionService predictionService;

    @GetMapping("/predictions")
    public ResponseEntity<?> getAllPredictions() {
        List<PredictionDto> predictions = predictionService.getAllPredictions();
        return new ResponseEntity<List<PredictionDto>>(predictions, HttpStatus.OK);
    }

    @GetMapping("/predictions/{userId}/{matchId}")
    public ResponseEntity<?> getPrediction(@PathVariable Long userId, @PathVariable Long matchId) {
        return new ResponseEntity<PredictionDto>(predictionService.getPrediction(userId, matchId), HttpStatus.OK);
    }

    @PostMapping("/predictions")
    public ResponseEntity<?> createPrediction(@RequestBody PredictionDto prediction) {
        final boolean result = predictionService.create(prediction);
        return result ? new ResponseEntity<PredictionDto>(HttpStatus.CREATED) : new ResponseEntity<PredictionDto>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/predictions")
    public ResponseEntity<?> updatePrediction(@RequestBody PredictionDto prediction) {
        predictionService.update(prediction);
        return new ResponseEntity<PredictionDto>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/predictions/{id}")
    public ResponseEntity<?> deletePrediction(@PathVariable Long id) {
        predictionService.delete(id);
        return new ResponseEntity<PredictionDto>(HttpStatus.NO_CONTENT);
    }
}
