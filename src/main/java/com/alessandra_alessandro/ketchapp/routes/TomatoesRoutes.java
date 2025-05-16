package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.TomatoesControllers;
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

    @Operation(summary = "Get all tomatoes records", description = "Fetches all tomatoes records from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tomatoes records",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TomatoDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<TomatoDto>> getTomatoes() {
        try {
            List<TomatoDto> tomatoes = tomatoesController.getTomatoes();
            return ResponseEntity.ok(tomatoes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get tomatoes records by Id", description = "Fetches tomatoes records by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tomatoes records",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TomatoDto.class))),
            @ApiResponse(responseCode = "404", description = "Tomatoes records not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TomatoDto> getTomato(@PathVariable Integer id) {
        try {
            TomatoDto getTomato = tomatoesController.getTomato(id);
            if (getTomato != null) {
                return ResponseEntity.ok(getTomato);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Create a new tomato record", description = "Creates a new tomato record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created tomato record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TomatoDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TomatoDto> createTomato(TomatoDto tomatoDtoToCreate) {
        try {
            TomatoDto createdTomato = tomatoesController.createTomato(tomatoDtoToCreate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTomato);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete a tomato record by Id", description = "Deletes a tomato record by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted tomato record"),
            @ApiResponse(responseCode = "404", description = "Tomato record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTomato(@PathVariable Integer id) {
        try {
            TomatoDto deletedTomato = tomatoesController.deleteTomato(id);
            if (deletedTomato != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
