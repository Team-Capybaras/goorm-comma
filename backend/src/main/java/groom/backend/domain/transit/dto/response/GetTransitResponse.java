package groom.backend.domain.transit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 대중교통 정보 조회 응답 DTO
 * 지역의 지하철역, 버스 정류장, 공유 자전거 정보를 포함합니다.
 */
@Schema(
        name = "GetTransitResponse",
        description = "대중교통 정보 조회 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTransitResponse {
    @Schema(
            description = "지역 코드 (AREA_CODE)",
            example = "POI093"
    )
    private String areaCode;

    @Schema(
            description = "공원명",
            example = "뚝섬한강공원"
    )
    private String areaName;

    @Schema(
            description = "지하철역 정보 리스트"
    )
    private List<SubwayStationInfo> subwayStations;

    @Schema(
            description = "버스 정류장 정보 리스트"
    )
    private List<BusStationInfo> busStations;

    @Schema(
            description = "공유 자전거 정보 리스트"
    )
    private List<SbikeInfo> sbikes;

    /**
     * 지하철역 정보
     */
    @Schema(
            name = "SubwayStationInfo",
            description = "지하철역 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubwayStationInfo {
        @Schema(
                description = "지하철역 ID",
                example = "12345"
        )
        private Integer subId;

        @Schema(
                description = "지하철역명",
                example = "뚝섬역"
        )
        private String subStnName;

        @Schema(
                description = "지하철 노선",
                example = "2호선"
        )
        private String subStnLine;

        @Schema(
                description = "주소",
                example = "서울특별시 광진구 자양동"
        )
        private String addr;

        @Schema(
                description = "도로명 주소",
                example = "서울특별시 광진구 강변북로 50"
        )
        private String roadAddr;

        @Schema(
                description = "경도",
                example = "127.1234567890"
        )
        private BigDecimal subStnX;

        @Schema(
                description = "위도",
                example = "37.1234567890"
        )
        private BigDecimal subStnY;

        @Schema(
                description = "지하철 시설 정보 리스트"
        )
        private List<SubwayFacilityInfo> facilities;
    }

    /**
     * 지하철 시설 정보
     */
    @Schema(
            name = "SubwayFacilityInfo",
            description = "지하철 시설 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubwayFacilityInfo {
        @Schema(
                description = "지하철 시설 정보 ID",
                example = "12345"
        )
        private Integer subFacilityInfo;

        @Schema(
                description = "승강기명",
                example = "1번 출입구 엘리베이터"
        )
        private String elvtrName;

        @Schema(
                description = "운영 구간",
                example = "1-2"
        )
        private String operateSector;

        @Schema(
                description = "설치 위치",
                example = "1번 출입구"
        )
        private String installPosition;

        @Schema(
                description = "사용 가능 여부",
                example = "Y"
        )
        private String useYn;

        @Schema(
                description = "승강기 구분 (EV: 엘리베이터, ES: 에스컬레이터)",
                example = "EV"
        )
        private String elvtrSection;
    }

    /**
     * 버스 정류장 정보
     */
    @Schema(
            name = "BusStationInfo",
            description = "버스 정류장 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BusStationInfo {
        @Schema(
                description = "버스 정류장 ID",
                example = "12345"
        )
        private Integer busStnId;

        @Schema(
                description = "버스 ARS ID",
                example = "12345"
        )
        private Integer busArsId;

        @Schema(
                description = "버스 정류장명",
                example = "12345"
        )
        private Integer busStnName;

        @Schema(
                description = "경도",
                example = "127.1234567890"
        )
        private BigDecimal busStnX;

        @Schema(
                description = "위도",
                example = "37.1234567890"
        )
        private BigDecimal busStnY;
    }

    /**
     * 공유 자전거 정보
     */
    @Schema(
            name = "SbikeInfo",
            description = "공유 자전거 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SbikeInfo {
        @Schema(
                description = "공유 자전거 스팟 ID",
                example = "SPOT001"
        )
        private String sbikeSpotId;

        @Schema(
                description = "공유 자전거 스팟명",
                example = "뚝섬한강공원 스팟"
        )
        private String sbikeSpotName;

        @Schema(
                description = "공유 자전거 수용 대수",
                example = "20"
        )
        private Integer sbikeCapacity;

        @Schema(
                description = "경도",
                example = "127.1234567890"
        )
        private BigDecimal sbikeX;

        @Schema(
                description = "위도",
                example = "37.1234567890"
        )
        private BigDecimal sbikeY;

        @Schema(
                description = "공유 자전거 현황 정보"
        )
        private SbikeStatusInfo status;
    }

    /**
     * 공유 자전거 현황 정보
     */
    @Schema(
            name = "SbikeStatusInfo",
            description = "공유 자전거 현황 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SbikeStatusInfo {
        @Schema(
                description = "데이터 수집 시간",
                example = "2026-01-07T10:50:00"
        )
        private LocalDateTime dataGetTime;

        @Schema(
                description = "공유 자전거 주차 비율 (%)",
                example = "75"
        )
        private Integer sbikeParkingPer;

        @Schema(
                description = "공유 자전거 주차 대수",
                example = "15"
        )
        private Integer sbikeParkingCnt;
    }
}

