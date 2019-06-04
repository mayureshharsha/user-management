package com.uam.predictionapp.controller;

import com.uam.predictionapp.model.dto.ResultDto;
import com.uam.predictionapp.service.ResultService;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("${resultMgmt.baseUrl}")
public class ResultsController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/results")
    public ResponseEntity<?> getAllResults() {
        List<ResultDto> results = resultService.listResults();
        results.sort(Comparator.comparingLong(ResultDto::getPoints)
                .reversed());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/results/{userId}")
    public ResponseEntity<?> getAllResults(@PathVariable Long userId) {
        Long point = resultService.getPoint(userId);
        return new ResponseEntity<>(point, HttpStatus.OK);
    }

    @PostMapping("/results/calculate")
    public ResponseEntity<?> calculateResults() {
        resultService.calculate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
