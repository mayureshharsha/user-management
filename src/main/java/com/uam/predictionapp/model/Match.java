package com.uam.predictionapp.model;

import com.uam.predictionapp.contants.Predict;
import lombok.Data;

import java.util.Date;

@Data
public class Match {
    private Long matchId;

    private String venue;

    private Team homeTeam;

    private Team awayTeam;

    private Date dateTime;

    private Predict homeResult;
}
