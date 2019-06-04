package com.uam.predictionapp.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResultDto {
    private String username;
    private Long points;
}