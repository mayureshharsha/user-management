package com.uam.predictionapp.controller;

import com.uam.predictionapp.model.dto.ResultDto;
import com.uam.predictionapp.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${resultMgmt.baseUrl}")
public class ResultsController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/results")
    public ResponseEntity<?> getAllResults() {
        List<ResultDto> results = resultService.listResults();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/results/calculate")
    public ResponseEntity<?> calculateResults() {
        resultService.calculate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
