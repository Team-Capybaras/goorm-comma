package groom.backend.application.avoidance.entity;

import groom.backend.application.avoidance.enums.Weekday;
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