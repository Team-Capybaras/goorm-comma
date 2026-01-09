package groom.backend.domain.weather.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(
        name = "WeatherStatusResponse",
        description = "날씨 및 대기환경 상태 응답 DTO"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherStatusResponse {

  @Schema(
          description = "데이터 수집 시각",
          example = "2026-01-06T09:00:00"
  )
  private LocalDateTime dataGetTime;

  @Schema(
          description = "날씨 정보 기준 시각",
          example = "2026-01-06T09:00:00"
  )
  private LocalDateTime weatherTime;

  @Schema(
          description = "기온 (℃)",
          example = "2.5"
  )
  private Float temp;

  @Schema(
          description = "체감 온도 (℃)",
          example = "-1.3"
  )
  private Float sensibleTemp;

  @Schema(
          description = "습도 (%)",
          example = "65"
  )
  private Integer humidity;

  @Schema(
          description = "풍향",
          example = "NW"
  )
  private String windDirct;

  @Schema(
          description = "풍속 (m/s)",
          example = "3.2"
  )
  private Float windSpd;

  @Schema(
          description = "강수량",
          example = "-"
  )
  private String precipitation;

  @Schema(
          description = "금일 강수확률(max) (%)",
          example = "80"
  )
  private Integer rainChance;

  @Schema(
          description = "강수 유형",
          example = "눈"
  )
  private String precptType;

  @Schema(
          description = "강수 관련 메시지",
          example = "눈이 내리고 있습니다"
  )
  private String precptMsg;

  @Schema(
          description = "자외선 지수 레벨",
          example = "1"
  )
  private Integer uvIndexLevel;

  @Schema(
          description = "자외선 지수 등급",
          example = "낮음"
  )
  private String uvIndex;

  @Schema(
          description = "미세먼지(PM2.5) 등급",
          example = "보통"
  )
  private String pm25Index;

  @Schema(
          description = "미세먼지(PM2.5) 농도 (㎍/㎥)",
          example = "18"
  )
  private Integer pm25;

  @Schema(
          description = "초미세먼지(PM10) 등급",
          example = "좋음"
  )
  private String pm10Index;

  @Schema(
          description = "초미세먼지(PM10) 농도 (㎍/㎥)",
          example = "32"
  )
  private Integer pm10;

  @Schema(
          description = "통합 대기환경 등급",
          example = "보통"
  )
  private String airIndex;

  @Schema(
          description = "통합 대기환경 지수 수치",
          example = "85.5"
  )
  private Float airIndexLevel;

  @Schema(
          description = "대기질 지수 결정 주요 물질",
          example = "PM2.5"
  )
  private String airIndexMain;

  @Schema(
          description = "대기환경 종합 메시지",
          example = "외출 시 마스크 착용을 권장합니다"
  )
  private String airMsg;
}
