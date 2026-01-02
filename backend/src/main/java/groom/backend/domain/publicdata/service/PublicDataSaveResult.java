package groom.backend.domain.publicdata.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공공 데이터 저장 결과
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicDataSaveResult {
    private boolean parkSaved;
    private boolean livePopStatusSaved;
    private boolean predPopStatusSaved;
    private int predPopStatusCount;
    private boolean weatherStatusSaved;
    private boolean parkingLotSaved;
    private int parkingLotCount;
    private boolean subwayStationSaved;
    private boolean busStationSaved;
    private int busStationCount;
    private boolean sbikeSaved;
    private boolean chargerStationSaved;

    public static PublicDataSaveResult empty() {
        return new PublicDataSaveResult();
    }

    @Override
    public String toString() {
        return String.format(
                "PublicDataSaveResult{park=%s, livePop=%s, predPop=%s(count=%d), weather=%s, " +
                "parking=%s(count=%d), subway=%s, bus=%s(count=%d), sbike=%s, charger=%s}",
                parkSaved, livePopStatusSaved, predPopStatusSaved, predPopStatusCount, weatherStatusSaved,
                parkingLotSaved, parkingLotCount, subwayStationSaved, busStationSaved, busStationCount,
                sbikeSaved, chargerStationSaved
        );
    }
}

