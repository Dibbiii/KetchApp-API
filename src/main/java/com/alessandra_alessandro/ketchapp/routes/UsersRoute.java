package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controller.UsersController;
import com.alessandra_alessandro.ketchapp.models.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersRoute {

    private final UsersController usersController;

    @Autowired
    public UsersRoute(UsersController usersController) {
        this.usersController = usersController;
    }

    @Operation(summary = "Get all user records", description = "Fetches all user records from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user records"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> users = usersController.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get user by UUID", description = "Fetches a user record by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user record"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("uuid") UUID uuid) {
        try {
            UserDto user = usersController.getUserById(uuid);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create a new user", description = "Creates a new user record in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created user record"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create/{username}")
    public ResponseEntity<UserDto> createUser(@PathVariable("username") String username) {
        try {
            UserDto user = usersController.createUser(username);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

