package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 재난 안전 메시지 DTO
 * 
 * XML 구조:
 * <LIVE_DST_MESSAGE>
 *   <LIVE_DST_MESSAGE>...</LIVE_DST_MESSAGE>
 * </LIVE_DST_MESSAGE>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisasterMessageDto {
    
    @JacksonXmlProperty(localName = "LIVE_DST_MESSAGE")
    private DisasterMessageDetailDto liveDstMessage;
}
