package com.uam.predictionapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PredictionId implements Serializable {
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(name = "match_id")
    private Long matchId;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof PredictionId)) return false;
        PredictionId that = (PredictionId) o;
        return Objects.equals(getUserEntity().getId(), that.getUserEntity().getId()) &&
                Objects.equals(getMatchId(), that.getMatchId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserEntity().getId(), getMatchId());
    }
}
