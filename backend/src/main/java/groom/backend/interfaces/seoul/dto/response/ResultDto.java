package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * API 결과 정보 DTO
 * 
 * XML 구조:
 * <RESULT>
 *   <RESULT.CODE>...</RESULT.CODE>
 *   <RESULT.MESSAGE>...</RESULT.MESSAGE>
 * </RESULT>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    
    /**
     * 결과 코드 (예: "INFO-000")
     * XML: <RESULT.CODE>
     */
    @JacksonXmlProperty(localName = "RESULT.CODE")
    private String code;

    /**
     * 결과 메시지 (예: "정상 처리되었습니다.")
     * XML: <RESULT.MESSAGE>
     */
    @JacksonXmlProperty(localName = "RESULT.MESSAGE")
    private String message;
}

