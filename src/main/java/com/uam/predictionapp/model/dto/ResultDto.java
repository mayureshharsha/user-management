package com.uam.predictionapp.model.dto;

import com.uam.predictionapp.contants.RankStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResultDto {
    private String username;
    private Long points;
    private int rank;
    private RankStatus rankStatus;
}
