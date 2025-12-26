package groom.backend.domain.location.controller;

import groom.backend.domain.location.dto.response.LocationResponse;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/locations")
@Tag(name = "Location", description = "위치 관리 API (test)")
public class LocationController {


    @GetMapping
    @Operation(
            summary = "test api",
            description = "공통 응답 반환 테스트 api"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "요청")
    })
    public LocationResponse createLocation() {
        return new LocationResponse("success message");
    }
}
