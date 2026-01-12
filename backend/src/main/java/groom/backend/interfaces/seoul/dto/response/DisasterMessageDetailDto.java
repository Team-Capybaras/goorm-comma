package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 재난 안전 메시지 상세 정보 DTO
 * 
 * XML 구조:
 * <LIVE_DST_MESSAGE>
 *   <DST_SE_NM>...</DST_SE_NM>
 *   <EMRG_STEP_NM>...</EMRG_STEP_NM>
 *   <MSG_CN>...</MSG_CN>
 *   <CRT_DT>...</CRT_DT>
 * </LIVE_DST_MESSAGE>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DisasterMessageDetailDto {
    
    /**
     * 재난 구분명 (한파, 폭염, 태풍 등)
     * XML: <DST_SE_NM>
     */
    @JacksonXmlProperty(localName = "DST_SE_NM")
    private String dstSeNm;

    /**
     * 비상 단계명
     * XML: <EMRG_STEP_NM>
     */
    @JacksonXmlProperty(localName = "EMRG_STEP_NM")
    private String emrgStepNm;

    /**
     * 메시지 내용
     * XML: <MSG_CN>
     */
    @JacksonXmlProperty(localName = "MSG_CN")
    private String msgCn;

    /**
     * 생성 일시
     * XML: <CRT_DT>
     */
    @JacksonXmlProperty(localName = "CRT_DT")
    private String crtDt;
}

