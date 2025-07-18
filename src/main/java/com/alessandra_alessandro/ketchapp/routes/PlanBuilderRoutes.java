package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.PlanBuilderControllers;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
public class PlanBuilderRoutes {
    private final PlanBuilderControllers planBuilderControllers;

    @Autowired
    public PlanBuilderRoutes(PlanBuilderControllers planBuilderControllers) {
        this.planBuilderControllers = planBuilderControllers;
    }

    @Operation(summary = "Create a new plan builder", description = "Creates a new plan builder with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved plans",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlanBuilderRequestDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (authentication required)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PlanBuilderRequestDto> createPlanBuilder(@Valid @RequestBody PlanBuilderResponseDto planBuilderResponseDto) {
        return planBuilderControllers.createPlanBuilder(planBuilderResponseDto);
    }
}