package groom.backend.domain.recommendation.entity;

import groom.backend.domain.recommendation.enums.Weekday;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParkStatisticsId implements Serializable {
  private Weekday weekday;
  private int hour;
  private String areaCode;
}