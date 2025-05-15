package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controller.UsersController;
import com.alessandra_alessandro.ketchapp.models.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

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
}