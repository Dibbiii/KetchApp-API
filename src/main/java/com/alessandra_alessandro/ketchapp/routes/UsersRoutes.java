package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.UsersControllers;
import com.alessandra_alessandro.ketchapp.models.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersRoutes {

    private final UsersControllers usersController;

    @Autowired
    public UsersRoutes(UsersControllers usersController) {
        this.usersController = usersController;
    }

    @Operation(
        summary = "Get all users",
        description = "Fetches a list of all user records.",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved user records",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsersDto.class)
                )
            ),
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
    @GetMapping
    public ResponseEntity<List<UsersDto>> getUsers() {
        try {
            List<UsersDto> users = usersController.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
        summary = "Get user by UUID",
        description = "Fetches a user record by its UUID.",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved user record",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized (authentication required)"
            ),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUser(@PathVariable("uuid") UUID uuid) {
        try {
            UserDto user = usersController.getUser(uuid);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }

    @Operation(
        summary = "Get email by username",
        description = "Fetches the email address associated with a given username.",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved email address",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized (authentication required)"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Username not found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/email/{username}")
    public ResponseEntity<String> getEmailByUsername(
        @PathVariable String username
    ) {
        try {
            String response = usersController.getEmailByUsername(username);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }

    @Operation(
        summary = "Get tomatoes by user UUID",
        description = "Fetches a list of tomatoes for a specific user by their UUID. Optionally filter by date (yyyy-MM-dd) with ?date= or by date range with ?startDate=yyyy-MM-dd&endDate=yyyy-MM-dd (both required for range)",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved tomatoes for user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TomatoDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized (authentication required)"
            ),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/{uuid}/tomatoes")
    public ResponseEntity<List<TomatoDto>> getUserTomatoes(
        @PathVariable UUID uuid,
        @RequestParam(value = "date", required = false) LocalDate date,
        @RequestParam(
            value = "startDate",
            required = false
        ) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        try {
            List<TomatoDto> tomatoes;
            if (startDate != null && endDate != null) {
                tomatoes = usersController.getUserTomatoes(
                    uuid,
                    startDate,
                    endDate
                );
            } else if (date != null) {
                tomatoes = usersController.getUserTomatoes(uuid, date);
            } else {
                tomatoes = usersController.getUserTomatoes(uuid);
            }
            return ResponseEntity.ok(tomatoes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
        summary = "Get activities by user UUID",
        description = "Fetches a list of activities for a specific user by their UUID.",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved activities for user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ActivityDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized (authentication required)"
            ),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/{uuid}/activities")
    public ResponseEntity<List<ActivityDto>> getUserActivities(
        @PathVariable UUID uuid
    ) {
        try {
            List<ActivityDto> activities = usersController.getUserActivities(
                uuid
            );
            if (activities != null) {
                return ResponseEntity.ok(activities);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }

    @Operation(
        summary = "Get achievements by user UUID",
        description = "Fetches a list of achievements for a specific user by their UUID.",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved achievements for user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AchievementDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized (authentication required)"
            ),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/{uuid}/achievements")
    public ResponseEntity<List<AchievementDto>> getUserAchievements(
        @PathVariable UUID uuid
    ) {
        try {
            List<AchievementDto> achievements =
                usersController.getUserAchievements(uuid);
            if (achievements != null) {
                return ResponseEntity.ok(achievements);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }

    @Operation(
        summary = "Get statistics by user UUID",
        description = "Fetches statistics for a specific user by their UUID and a date range.",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved statistics for user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StatisticsDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized (authentication required)"
            ),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @GetMapping("/{uuid}/statistics")
    public ResponseEntity<StatisticsDto> getUserStatistics(
        @PathVariable UUID uuid,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate
    ) {
        try {
            StatisticsDto statistics = usersController.getUserStatistics(
                uuid,
                startDate,
                endDate
            );
            if (statistics != null) {
                return ResponseEntity.ok(statistics);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }

    @Operation(
        summary = "Create a new user",
        description = "Creates a new user record.",
        tags = { "Users" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "User created successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = NewUserDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input data"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error"
            ),
        }
    )
    @PostMapping
    public ResponseEntity<NewUserDto> createUser(
        @RequestBody NewUserDto newUserDto
    ) {
        try {
            NewUserDto created = usersController.createUser(newUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
            ).build();
        }
    }
}
