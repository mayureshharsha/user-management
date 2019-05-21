package com.uam.predictionapp.model.dto;

import com.uam.predictionapp.contants.Predict;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PredictionDto {

    private Long id;

    private Long user_id;

    private Long matchId;

    private Predict homeResult;
}
