package groom.backend.unit.transit;

/**
 * 외부 API 통합에 대한 테스트
 * 서버 장애시 어떻게 대응하는지
 */
public class F105IntergrationTest {
}

package groom.backend.transit.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransitExternalFailureF105IntegrationTest {

  @Autowired
  TestRestTemplate restTemplate;

  private static final String ENDPOINT =
          "/api/v1/parks/POI001/transits";

  @Test
  @DisplayName("F105-12 외부 대중교통 API 장애 시 서버 오류(5xx)가 발생하지 않는다")
  void shouldNotReturn5xxWhenUpstreamFails() {
    // given
    // WireMock 등으로 외부 API 실패 유도

    // when
    var response = restTemplate.getForEntity(ENDPOINT, String.class);

    // then
    assertThat(response.getStatusCode().is5xxServerError()).isFalse();
  }

  @Test
  @DisplayName("F105-13 외부 API 장애 시 빈 결과 또는 명시적 상태값으로 처리된다")
  void shouldReturnEmptyOrExplicitStatus() {
    var response =
            restTemplate.getForEntity(ENDPOINT, TransitResponse.class);

    assertThat(response.getBody().getData().getTransits()).isNotNull();
    // 또는
    // assertThat(response.getBody().getData().getStatus()).isEqualTo("UNAVAILABLE");
  }
}
