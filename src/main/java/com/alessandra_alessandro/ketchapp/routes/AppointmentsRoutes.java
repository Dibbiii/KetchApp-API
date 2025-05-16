package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.AppointmentsControllers;
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

@RestController
@RequestMapping("/api/appointments")
public class AppointmentsRoutes {

    private final AppointmentsControllers appointmentsController;

    @Autowired
    public AppointmentsRoutes(AppointmentsControllers appointmentsController) {
        this.appointmentsController = appointmentsController;
    }

    @Operation(summary = "Get all appoinetments records", description = "Fetches all appointments records from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved appointment records"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointments() {
        try {
            List<AppointmentDto> appointments = appointmentsController.getAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get an appointment by id", description = "Fetches an appointment by its ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved appointment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppointmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable Integer id) {
        try {
            AppointmentDto appointment = appointmentsController.getAppointment(id);
            return ResponseEntity.ok(appointment);
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
    @PostMapping
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

    @Operation(summary = "Delete an appointment by id", description = "Deletes an appointment by its ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted appointment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppointmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDto> deleteAppointment(@PathVariable Integer id) {
        try {
            AppointmentDto deletedAppointment = appointmentsController.deleteAppointment(id);
            if (deletedAppointment != null) {
                return ResponseEntity.ok(deletedAppointment);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}