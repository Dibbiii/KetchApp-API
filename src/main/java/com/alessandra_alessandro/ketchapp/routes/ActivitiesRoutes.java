package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.ActivitiesControllers;
import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesRoutes {

    private final ActivitiesControllers activitiesController;

    @Autowired
    public ActivitiesRoutes(ActivitiesControllers activitiesController) {
        this.activitiesController = activitiesController;
    }

    @Operation(
        summary = "Create a new activity",
        description = "Creates a new activity record.",
        tags = { "Activities" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Successfully created achievement record",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ActivityDto.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized (authentication required)"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @PostMapping
    public ResponseEntity<ActivityDto> createActivity(
        @RequestBody ActivityDto activityDtoToCreate
    ) {
        try {
            ActivityDto createdActivity = activitiesController.createActivity(
                activityDtoToCreate
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(
                createdActivity
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }
}
