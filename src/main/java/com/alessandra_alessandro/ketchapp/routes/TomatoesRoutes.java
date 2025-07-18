package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.TomatoesControllers;
import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.dto.TomatoDto;
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
@RequestMapping("/api/tomatoes")
public class TomatoesRoutes {
    private final TomatoesControllers tomatoesController;

    @Autowired
    public TomatoesRoutes(TomatoesControllers tomatoesController) {
        this.tomatoesController = tomatoesController;
    }

    @Operation(summary = "Create a new tomato record", description = "Creates a new tomato record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created tomato record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TomatoDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (authentication required)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TomatoDto> createTomato(@RequestBody TomatoDto tomatoDtoToCreate) {
        try {
            TomatoDto createdTomato = tomatoesController.createTomato(tomatoDtoToCreate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTomato);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get all activities for a tomato", description = "Fetches all activities related to a specific tomato by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved activities",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (authentication required)"),
            @ApiResponse(responseCode = "404", description = "Tomato or activities not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDto>> getActivitiesByTomatoId(@PathVariable Integer id) {
        try {
            List<ActivityDto> activities = tomatoesController.getActivitiesByTomatoId(id);
            if (activities != null) {
                return ResponseEntity.ok(activities);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
