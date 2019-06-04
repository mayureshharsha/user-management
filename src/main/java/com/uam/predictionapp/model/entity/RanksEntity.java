package com.uam.predictionapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uam.predictionapp.contants.RankStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Table(
        name = "Ranks",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})}
)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RanksEntity {
    @Id
    private String username;
    @Column(name = "current_rank")
    private int currentRank;
    @Column(name = "previous_rank")
    private int previousRank;
    @Column(name = "previous_date")
    private Date previousDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "rank_status")
    private RankStatus rankStatus;
}
