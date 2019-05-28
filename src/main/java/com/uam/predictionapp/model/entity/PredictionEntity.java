package com.uam.predictionapp.model.entity;

import javax.persistence.*;

import com.uam.predictionapp.contants.MatchPredict;
import com.uam.predictionapp.contants.TossPredict;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Prediction")
@NoArgsConstructor
@Data
public class PredictionEntity {

    @EmbeddedId
    private PredictionId id;

    @Column(name = "home_result")
    private MatchPredict homeResult;

    @Column(name = "toss_result")
    private TossPredict tossResult;

    @Column(name = "mom_result")
    private String momResult;
}
