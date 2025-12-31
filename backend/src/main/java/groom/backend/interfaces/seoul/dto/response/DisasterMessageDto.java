package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 재난 안전 메시지 DTO
 * 
 * XML 구조:
 * <LIVE_DST_MESSAGE>
 *   <LIVE_DST_MESSAGE>
 *     <DST_SE_NM>...</DST_SE_NM>
 *     <EMRG_STEP_NM>...</EMRG_STEP_NM>
 *     <MSG_CN>...</MSG_CN>
 *     <CRT_DT>...</CRT_DT>
 *   </LIVE_DST_MESSAGE>
 * </LIVE_DST_MESSAGE>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DisasterMessageDto {
    
    /**
     * 재난 안전 메시지 상세 정보
     * XML: <LIVE_DST_MESSAGE><LIVE_DST_MESSAGE>
     */
    @JacksonXmlProperty(localName = "LIVE_DST_MESSAGE")
    private DisasterMessageDetailDto liveDstMessage;
}

