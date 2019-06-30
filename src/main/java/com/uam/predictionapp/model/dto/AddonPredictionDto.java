package com.uam.predictionapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddonPredictionDto {

    private Integer id;

    @NotNull
    private Long userId;

    private String POT;

    private Date POTDate;

    private String HRG;

    private Date HRGDate;

    private String HWT;

    private Date HWTDate;

}
