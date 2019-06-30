package com.uam.predictionapp.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AddonPrediction")
@NoArgsConstructor
@Data
public class AddonPredictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity userEntity;

    String POT;

    Date POTDate;

    String HRG;

    Date HRGDate;

    String HWT;

    Date HWTDate;
}
