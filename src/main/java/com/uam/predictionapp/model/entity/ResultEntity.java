package com.uam.predictionapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@Table(
        name = "Result",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userId"})}
)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;

    private Long points;

    private int rank;
}
