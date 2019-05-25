package com.uam.predictionapp.service;

import com.uam.predictionapp.endpoint.MatchesClient;
import com.uam.predictionapp.model.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchService {
    private final MatchesClient matchesClient;

    private List<Match> matches;

    public void loadMatches(){
        matches = matchesClient.getMatches();
    }

    public Optional<Match> getMatch(Long id){
        if (matches == null){
            loadMatches();
        }
        Stream<Match> matchStream = matches.parallelStream().filter(match -> match.getMatchId().equals(id));
        return matchStream.findFirst();
    }
}
