package com.uam.predictionapp.model.entity;

import javax.persistence.*;

import com.uam.predictionapp.contants.Predict;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="Prediction")
@NoArgsConstructor
@Data
public class PredictionEntity {

    @EmbeddedId
    private PredictionId id;

    @Column(name = "home_result")
    private Predict homeResult;
}
