package groom.backend.interfaces.seoul;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import groom.backend.interfaces.seoul.dto.request.SeoulCityDataRequest;
import groom.backend.interfaces.seoul.dto.response.SeoulCityDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 서울시 공공 API 클라이언트
 * 핫스팟 장소 정보를 조회하는 API를 호출합니다.
 */
@Service
@Slf4j
public class SeoulApiClient {
    private final String seoulApiKey;
    private final RestClient restClient;
    private final XmlMapper xmlMapper;

    public SeoulApiClient(RestClient.Builder builder,
                         @Value("${api.seoul.url}") String seoulUrl,
                         @Value("${api.seoul.api-key}") String seoulApiKey,
                         @Value("${api.seoul.connection-timeout}") Integer connectionTimeoutMs,
                         @Value("${api.seoul.read-timeout}") Integer readTimeoutMs,
                         @Value("${api.seoul.bulkhead-thread-limit}") Integer connLimit) {

        // 1. 커넥션 풀 매니저(HttpClient5 전용)
        var connManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(connLimit)        // bulkhead 패턴. 전체 동시 연결 connection pool
                .setMaxConnPerRoute(connLimit)     // Route별 연결 수
                .build();

        // 2. Timeout 설정 (HttpClient5 스타일)
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(connectionTimeoutMs))
                .setResponseTimeout(Timeout.ofMilliseconds(readTimeoutMs))
                .build();

        // 3. HttpClient 빌드
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        // 4. RestClient 반영
        this.restClient = builder
                .requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
                .baseUrl(seoulUrl)
                .build();

        this.seoulApiKey = seoulApiKey;
        
        // 5. XML 매퍼 초기화
        this.xmlMapper = new XmlMapper();
    }

    /**
     * 서울시 공공 API를 호출하여 핫스팟 장소 정보를 조회합니다.
     * XML 응답을 JSON으로 변환하여 반환합니다.
     * 
     * @param request 핫스팟 장소 조회 요청 정보
     * @return 서울시 공공 API 응답 (JSON 형식)
     */
    public SeoulCityDataResponse getCityData(SeoulCityDataRequest request) {
        // URL 형식: http://openapi.seoul.go.kr:8088/{API_KEY}/xml/citydata/{START_INDEX}/{END_INDEX}/{AREA_NM}
        // AREA_NM에 한글이나 공백이 포함될 수 있으므로 URL 인코딩 필요
        String areaNm = request.getAreaNm() != null ? request.getAreaNm() : "";
        
        // UriComponentsBuilder를 사용하여 자동으로 URL 인코딩
        URI uri = UriComponentsBuilder.fromPath("/{apiKey}/xml/citydata/{startIndex}/{endIndex}/{areaNm}")
                .encode(StandardCharsets.UTF_8)  // UTF-8 인코딩 보장
                .buildAndExpand(
                        seoulApiKey,
                        request.getStartIndex(),
                        request.getEndIndex(),
                        areaNm  // UriComponentsBuilder가 자동으로 인코딩
                )
                .toUri();

        String xmlResponse = restClient.get()
                .uri(uri)
                .header("Accept", "application/xml")
                .retrieve()
                .body(String.class);

        log.info("Seoul API 호출 성공 - AREA_NM: {}, 응답 길이: {}", request.getAreaNm(), 
                xmlResponse != null ? xmlResponse.length() : 0);

        // XML을 JSON으로 변환
        Map<String, Object> jsonData = convertXmlToJson(xmlResponse);
        
        SeoulCityDataResponse response = new SeoulCityDataResponse();
        response.setData(jsonData);
        return response;
    }

    /**
     * XML 문자열을 JSON Map으로 변환합니다.
     * 
     * @param xmlString XML 형식의 문자열
     * @return JSON으로 변환된 Map 객체
     */
    private Map<String, Object> convertXmlToJson(String xmlString) {
        try {
            // XML을 Map으로 읽기
            Map<String, Object> xmlMap = xmlMapper.readValue(xmlString, new TypeReference<Map<String, Object>>() {});
            
            log.debug("XML을 JSON으로 변환 완료");
            return xmlMap;
        } catch (Exception e) {
            log.error("XML을 JSON으로 변환 중 오류 발생: {}", e.getMessage(), e);
            // 변환 실패 시 빈 Map 반환
            return Map.of("error", "XML 파싱 실패", "message", e.getMessage());
        }
    }
}

