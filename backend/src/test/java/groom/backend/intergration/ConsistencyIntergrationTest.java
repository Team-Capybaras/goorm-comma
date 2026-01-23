package groom.backend.intergration;

import groom.backend.common.response.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsistencyIntergrationTest {
//  package groom.backend.intergration;
//
//import groom.backend.common.response.ApiResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//  /**
//   * 일관성 테스트.
//   * 멱등성 테스트의 경우 통합 시나리오로 이관
//   */
//  @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//  class F105ConsistencyTest {
//    @Autowired
//    TestRestTemplate restTemplate;
//
//    @Test
//    @DisplayName("F105 동일 지역 코드 재조회 시 응답이 일관된다")
//    void shouldReturnConsistentResult() {
//      String areaCode = "POI085";
//
//      var r1 = restTemplate.getForObject(
//              "/v1/transits?area_code="+areaCode,
//              ApiResponse.class
//      );
//
//      var r2 = restTemplate.getForObject(
//              "/v1/transits?area_code="+areaCode,
//              ApiResponse.class
//      );
//
//      assertThat(r1).isEqualTo(r2);
//    }
//  }
//

}
