package com.uam.predictionapp.model.dto;

import com.uam.predictionapp.contants.MatchPredict;
import com.uam.predictionapp.contants.TossPredict;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PredictionDto {

    private Long userId;

    private Long matchId;

    private MatchPredict homeResult;

    private TossPredict tossResult;

    private String momResult;
}
