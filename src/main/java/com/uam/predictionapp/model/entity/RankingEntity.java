package com.uam.predictionapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Table(
        name = "Ranking",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})}
)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RankingEntity {
    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "current_rank")
    private int currentRank;

    @Column(name = "previous_rank")
    private int previousRank;

    @Column(name = "previous_date")
    private Date previousDate;
}
