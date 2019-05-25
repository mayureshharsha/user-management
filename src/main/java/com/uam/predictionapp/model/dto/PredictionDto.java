package com.uam.predictionapp.model.dto;

import com.uam.predictionapp.contants.MatchPredict;
import com.uam.predictionapp.contants.TossPredict;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class PredictionDto {

    @NotNull
    private Long userId;

    @NotNull
    @Range(min = 1, max = 48)
    private Long matchId;

    @NotNull
    private MatchPredict homeResult;

    @NotNull
    private TossPredict tossResult;

    private String momResult;
}