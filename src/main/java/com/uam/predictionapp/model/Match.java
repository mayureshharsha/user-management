package com.uam.predictionapp.model;

import com.uam.predictionapp.contants.MatchPredict;
import com.uam.predictionapp.contants.TossPredict;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class Match {
    private Long matchId;

    private String venue;

    private Team homeTeam;

    private Team awayTeam;

    private Date dateTime;

    private MatchPredict homeResult;

    private TossPredict tossResult;

    private String momResult;
}
/*
{
	"matchId": 1,
	"venue": "Kennington Oval, London",
	"homeTeam": {
		"name": "England",
		"flag": null,
		"captain": null
	},
	"awayTeam": {
		"name": "South Africa",
		"flag": null,
		"captain": null
	},
	"dateTime": "2019-05-30T09:30:00.000Z",
	"homeResult": null
}
 */