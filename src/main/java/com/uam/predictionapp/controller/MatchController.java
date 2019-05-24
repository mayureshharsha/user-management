package com.uam.predictionapp.controller;

import com.uam.predictionapp.endpoint.MatchesClient;
import com.uam.predictionapp.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("${match.baseUrl}")
public class MatchController {

    @Autowired
    MatchesClient matchesClient;

    @GetMapping(value = "/matches", produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllPredictions() {
        return new ResponseEntity<List<Match>>(matchesClient.getMatches(), HttpStatus.OK);
    }
}
