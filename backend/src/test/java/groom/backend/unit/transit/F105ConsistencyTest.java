package groom.backend.unit.transit;
/**
 * 응답 일관성 테스트
 */

package groom.backend.transit.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransitConsistencyF105IntegrationTest {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  @DisplayName("F105-14 동일 공원 재조회 시 대중교통 정보 응답이 일관된다")
  void shouldReturnConsistentResultOnRepeatedCalls() {
    TransitResponse r1 =
            restTemplate.getForObject(
                    "/api/v1/parks/POI001/transits",
                    TransitResponse.class
            );

    TransitResponse r2 =
            restTemplate.getForObject(
                    "/api/v1/parks/POI001/transits",
                    TransitResponse.class
            );

    assertThat(r1.getData().getTransits())
            .isEqualTo(r2.getData().getTransits());
  }
}

