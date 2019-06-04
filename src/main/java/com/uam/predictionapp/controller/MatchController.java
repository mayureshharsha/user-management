package com.uam.predictionapp.controller;

import com.uam.predictionapp.endpoint.MatchesClient;
import com.uam.predictionapp.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${matchMgmt.baseUrl}")
public class MatchController {

    @Autowired
    MatchesClient matchesClient;

    @GetMapping(value = "/matches")
    public ResponseEntity<?> getAllMatches() {
        return new ResponseEntity<>(matchesClient.getMatches(), HttpStatus.OK);
    }
}