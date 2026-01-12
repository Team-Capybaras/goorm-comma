package groom.backend.application.avoidance.enums;

import java.time.DayOfWeek;

public enum Weekday {
  MON,
  TUE,
  WED,
  THU,
  FRI,
  SAT,
  SUN,
  ;

  /**
   * time.getDayofWeek 매칭
   * 1부터 시작, Monday부터
   * @param dayOfWeek
   * @return
   */
  public static Weekday from(DayOfWeek dayOfWeek) {
    return switch(dayOfWeek) {
      case MONDAY -> MON;
      case TUESDAY -> TUE;
      case WEDNESDAY -> WED;
      case THURSDAY -> THU;
      case FRIDAY -> FRI;
      case SATURDAY -> SAT;
      case SUNDAY -> SUN;
    };
  }
}
