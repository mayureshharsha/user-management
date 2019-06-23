package com.uam.predictionapp.service;

import com.uam.predictionapp.endpoint.MatchesClient;
import com.uam.predictionapp.model.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchService {
    private final MatchesClient matchesClient;

    private List<Match> matches;

    public List<Match> loadMatches(){
        matches = matchesClient.getMatches();
        return matches;
    }

    public Optional<Match> getMatch(Long id){
        if (matches == null){
            loadMatches();
        }
        Stream<Match> matchStream = matches.parallelStream().filter(match -> match.getMatchId().equals(id));
        return matchStream.findFirst();
    }

    public List<Match> getLatestMatchPlayed(int count) {
        loadMatches();
        List<Match> matches = this.matches;
        matches.sort(Comparator.comparingLong(Match::getMatchId).reversed());
        final List<Match> sortedMatches = matches.stream().filter(match -> match.getHomeResult() != null).collect(Collectors.toList());
        return sortedMatches.subList(0, count);
    }
}
