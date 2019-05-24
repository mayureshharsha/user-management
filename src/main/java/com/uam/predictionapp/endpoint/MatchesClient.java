package com.uam.predictionapp.endpoint;

import com.uam.predictionapp.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MatchesClient {

    public static final String DATA_MATCHES_JSON_URL = "https://raw.githubusercontent.com/aniket7777777/cricpred/master/Data_Matches.json";

    @Autowired
    RestTemplate restTemplate;

    public List<Match> getMatches() {
        final ResponseEntity<List<Match>> responseEntity = restTemplate.exchange(DATA_MATCHES_JSON_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Match>>() {
                });
        return responseEntity.getBody();
    }
}
