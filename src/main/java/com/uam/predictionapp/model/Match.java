package com.uam.predictionapp.model;

import com.uam.predictionapp.contants.Predict;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    private String venue;

    private Team homeTeam;

    private Team awayTeam;

    private Date dateTime;

    private Predict homeResult;
}
