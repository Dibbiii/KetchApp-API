package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.AchievementsController;
import com.alessandra_alessandro.ketchapp.models.dto.AchievementDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/achievements")
public class AchievementRoute {

    private  final AchievementsController achievementsController;

    @Autowired
    public AchievementRoute(AchievementsController achievementsController) {
        this.achievementsController = achievementsController;
    }

    @Operation(summary = "Get all achievements", description = "Fetches all achievements from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user records"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAllAchievements() {
        try {
            List<AchievementDto> achievements = achievementsController.getAllAchievements();
            return ResponseEntity.ok(achievements);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get achievement by UUID", description = "Fetches an achievement by user's UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved achievement"),
            @ApiResponse(responseCode = "404", description = "Achievement not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<AchievementDto> getAchievementByUserUUID(@PathVariable UUID uuid) {
        try {
            AchievementDto achievement = achievementsController.getAchievementByUserUUID(uuid);
            if (achievement != null) {
                return ResponseEntity.ok(achievement);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
