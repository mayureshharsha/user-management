package com.uam.predictionapp.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Addon-Prediction")
@NoArgsConstructor
@Data
public class AddonPredictionEntity {

    @Id
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity userEntity;

    String POT;

    String HRG;

    String HWT;
}
