package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.AppointmentsController;
import com.alessandra_alessandro.ketchapp.models.dto.AppointmentDto;
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
@RequestMapping("/api/appointments")
public class AppointmentsRoute {

    private final AppointmentsController appointmentsController;

    @Autowired
    public AppointmentsRoute(AppointmentsController appointmentsController) {
        this.appointmentsController = appointmentsController;
    }
    
    @Operation(summary = "Get all appointment records for a user", description = "Fetches all appointment records for a user from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved appointment records"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userUUID}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@PathVariable("userUUID") UUID userUUID) {
        try {
            List<AppointmentDto> appointments = appointmentsController.getAllAppointments(userUUID);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @Operation(summary = "Create a new appointment for a user", description = "Creates a new appointment for a user in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created appointment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppointmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request (e.g., invalid data)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        try {
            AppointmentDto createdAppointment = appointmentsController.createAppointment(appointmentDto);
            return ResponseEntity.ok(createdAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}