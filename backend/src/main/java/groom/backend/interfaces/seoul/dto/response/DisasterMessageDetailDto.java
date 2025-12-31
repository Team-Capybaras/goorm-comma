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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisasterMessageDetailDto {
    
    @JacksonXmlProperty(localName = "DST_SE_NM")
    private String dstSeNm;

    @JacksonXmlProperty(localName = "EMRG_STEP_NM")
    private String emrgStepNm;

    @JacksonXmlProperty(localName = "MSG_CN")
    private String msgCn;

    @JacksonXmlProperty(localName = "CRT_DT")
    private String crtDt;
}
