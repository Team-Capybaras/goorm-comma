package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 인구 예보 DTO
 * 
 * XML 구조:
 * <FCST_PPLTN>
 *   <FCST_PPLTN>...</FCST_PPLTN>
 *   ...
 * </FCST_PPLTN>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PopulationForecastDto {
    
    /**
     * 인구 예보 항목 리스트
     * XML: <FCST_PPLTN><FCST_PPLTN>...</FCST_PPLTN></FCST_PPLTN>
     */
    @JacksonXmlProperty(localName = "FCST_PPLTN")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PopulationForecastItemDto> fcstPpltn;
}
