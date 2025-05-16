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
import java.util.UUID;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesRoutes {
    private final ActivitiesControllers activitiesController;

    @Autowired
    public ActivitiesRoutes(ActivitiesControllers activitiesController) {
        this.activitiesController = activitiesController;
    }

    @Operation(summary = "Get all activities", description = "Fetches all activities from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved activities"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ActivityDto>> getAllActivities() {
        try {
            List<ActivityDto> activities = activitiesController.getAllActivities();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get activity by UUID", description = "Fetches an activity by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved activity"),
            @ApiResponse(responseCode = "404", description = "Activity not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<ActivityDto> getActivityByUUID(@PathVariable("uuid") UUID uuid) {
        try {
            ActivityDto activity = activitiesController.getActivityByUserUUID(uuid);
            if (activity != null) {
                return ResponseEntity.ok(activity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get activity by tomato ID", description = "Fetches an activity by its tomato ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved activities",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Activities not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/tomato/{tomatoId}")
    public ResponseEntity<List<ActivityDto>> getActivitiesByTomatoId(@PathVariable Integer tomatoId) {
        try {
            List<ActivityDto> activities = activitiesController.getActivitiesByTomatoId(tomatoId);
            if (activities != null && !activities.isEmpty()) {
                return ResponseEntity.ok(activities);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // TODO: See if we need to getAchievementById

    @Operation(summary = "Create a new activity", description = "Creates a new activity record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created achievement record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AchievementDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
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

    @Operation(summary = "Get activity by ID", description = "Fetches an activity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted activity"),
            @ApiResponse(responseCode = "404", description = "Activity not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityById(@PathVariable Integer id) {
        try {
            activitiesController.deleteActivityById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}