package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.PlanBuilderControllers;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanBuilderRoutes {
    private final PlanBuilderControllers planBuilderControllers;

    @Autowired
    public PlanBuilderRoutes(PlanBuilderControllers planBuilderControllers) {
        this.planBuilderControllers = planBuilderControllers;
    }

    @Operation(summary = "Get all plans", description = "Fetches all plans from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user records"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PlanBuilderRequestDto> createPlanBuilder(@RequestBody PlanBuilderResponseDto planBuilderResponseDto) {
        return planBuilderControllers.createPlanBuilder(planBuilderResponseDto);
    }
}