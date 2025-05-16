package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.ActivitiesControllers;
import com.alessandra_alessandro.ketchapp.models.dto.AchievementDto;
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

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesRoutes {
    private final ActivitiesControllers activitiesController;

    @Autowired
    public ActivitiesRoutes(ActivitiesControllers activitiesController) {
        this.activitiesController = activitiesController;
    }

    @Operation(summary = "Get all activities", description = "Fetches all activities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved activities",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ActivityDto>> getActivities() {
        try {
            List<ActivityDto> activities = activitiesController.getActivities();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get an activity by ID", description = "Fetches a specific activity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved activity",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityDto.class))),
            @ApiResponse(responseCode = "404", description = "Activity not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> getActivity(@PathVariable Integer id) {
        try {
            ActivityDto activity = activitiesController.getActivity(id);
            if (activity != null) {
                return ResponseEntity.ok(activity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create a new activity", description = "Creates a new activity record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created achievement record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AchievementDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDtoToCreate) {
        try {
            ActivityDto createdActivity = activitiesController.createActivity(activityDtoToCreate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update an existing activity", description = "Updates an existing activity record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated activity",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityDto.class))),
            @ApiResponse(responseCode = "404", description = "Activity not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Integer id) {
        try {
            ActivityDto deletedActivity = activitiesController.deleteActivity(id);
            if (deletedActivity != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}