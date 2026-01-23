package groom.backend.unit.transit;

import groom.backend.domain.transit.dto.response.GetTransitResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 정책에 대한 테스트
 *
 */
class F105PolicyTest {

  /**
   * 지도 표기를 위한 최소 정보 확인
   */
  @Test
  @DisplayName("F105 지도 표시 최소 정보는 식별자 + 좌표이다")
  void minimumMapInfoPolicy() {
    GetTransitResponse.SubwayStationInfo subway =
            GetTransitResponse.SubwayStationInfo.builder()
                    .subId(1)
                    .subStnX(BigDecimal.ONE)
                    .subStnY(BigDecimal.ONE)
                    .build();

    assertThat(subway.getSubId()).isNotNull();
    assertThat(subway.getSubStnX()).isNotNull();
    assertThat(subway.getSubStnY()).isNotNull();
  }
}

