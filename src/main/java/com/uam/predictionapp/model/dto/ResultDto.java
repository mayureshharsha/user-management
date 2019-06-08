package com.uam.predictionapp.model.dto;

import lombok.Data;

@Data
public class ResultDto {
    private String username;
    private Long points;
    private int currentRank;
    private int previousRank;

    public ResultDto(String username, Long points, int currentRank, int previousRank) {
        this.username = username;
        this.points = points;
        this.currentRank = currentRank;
        this.previousRank = previousRank;
    }
}
