package com.uam.predictionapp.model.entity;

import javax.persistence.*;

import com.uam.predictionapp.contants.Predict;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="Prediction")
@Table(
        name = "Prediction",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "match_id"})}
)
@NoArgsConstructor
@Data
public class PredictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "home_result")
    private Predict homeResult;
}
