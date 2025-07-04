package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.AchievementsControllers;
import com.alessandra_alessandro.ketchapp.models.dto.AchievementDto;
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
@RequestMapping("/api/achievements")
public class AchievementsRoutes {
    @Autowired
    AchievementsControllers achievementsController;

    @Operation(summary = "Get all achievements", description = "Fetches all achievements from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user records"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAchievements() {
        try {
            List<AchievementDto> achievements = achievementsController.getAchievements();
            return ResponseEntity.ok(achievements);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Create a new achievement", description = "Creates a new achievement in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created achievement",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AchievementDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(@RequestBody AchievementDto achievementDto) {
        try {
            AchievementDto createdAchievement = achievementsController.createAchievement(achievementDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAchievement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete an achievement", description = "Deletes an achievement by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted achievement",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AchievementDto.class))),
            @ApiResponse(responseCode = "404", description = "Achievement not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<AchievementDto> deleteAchievement(@PathVariable Integer id) {
        try {
            AchievementDto deletedAchievement = achievementsController.deleteAchievement(id);
            if (deletedAchievement != null) {
                return ResponseEntity.ok(deletedAchievement);
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