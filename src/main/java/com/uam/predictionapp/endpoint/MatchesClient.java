package com.uam.predictionapp.endpoint;

import com.uam.predictionapp.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MatchesClient {

    private final String DATA_MATCHES_JSON_URL;

    private final RestTemplate restTemplate;

    public MatchesClient(@Value("${dataJson.url}") String DATA_MATCHES_JSON_URL, @Autowired RestTemplate restTemplate) {
        this.DATA_MATCHES_JSON_URL = DATA_MATCHES_JSON_URL;
        this.restTemplate = restTemplate;
    }

    public List<Match> getMatches() {
        final ResponseEntity<List<Match>> responseEntity = restTemplate.exchange(DATA_MATCHES_JSON_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Match>>() {
                });
        return responseEntity.getBody();
    }
}