package com.uam.predictionapp.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResultDto {
    private String userName;
    private Long points;
}