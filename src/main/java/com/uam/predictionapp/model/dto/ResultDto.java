package com.uam.predictionapp.model.dto;

import lombok.Data;

@Data
public class ResultDto {
    private String username;
    private Long points;
    private int currentRank;
    private int previousRank;

    public ResultDto(String username, Long points, Integer currentRank, Integer previousRank) {
        this.username = username;
        this.points = points;
        if(currentRank != null){
            this.currentRank = currentRank;
        }
        if (previousRank != null){
            this.previousRank = previousRank;
        }
    }
}
