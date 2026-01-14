package groom.backend.domain.park.mapper;

import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;
import groom.backend.domain.park.entity.Park;
import org.springframework.stereotype.Component;

/**
 * 공원 엔티티와 DTO 간 변환을 담당하는 매퍼
 */
@Component
public class ParkMapper {

    /**
     * Park 엔티티를 ParkBasicInfo DTO로 변환합니다.
     *
     * @param park 공원 엔티티
     * @return 공원 기본 정보 DTO
     */
    public GetAllParksBasicResponse.ParkBasicInfo toParkBasicInfoDto(Park park) {
        if (park == null) {
            return null;
        }

        return GetAllParksBasicResponse.ParkBasicInfo.builder()
                .areaCode(park.getAreaCode())
                .areaName(park.getAreaName())
                .longitude(park.getLongitude())
                .latitude(park.getLatitude())
                .build();
    }
}
