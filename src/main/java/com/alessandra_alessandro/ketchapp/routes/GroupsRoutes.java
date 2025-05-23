package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.GroupsControllers;
import com.alessandra_alessandro.ketchapp.models.dto.GroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupsRoutes {
    private final GroupsControllers groupsController;

    @Autowired
    public GroupsRoutes(GroupsControllers groupsController) {
        this.groupsController = groupsController;
    }

    @Operation(summary = "Get all groups", description = "Fetches all groups from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved groups list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))),
            @ApiResponse(responseCode = "404", description = "Group list not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups() {
        try {
            List<GroupDto> groups = groupsController.getGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get group by ID", description = "Fetches a group by its ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved group",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Integer id) {
        try {
            GroupDto group = groupsController.getGroup(id);
            if (group != null) {
                return ResponseEntity.ok(group);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Create a new group", description = "Creates a new group in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created group",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        try {
            GroupDto createdGroup = groupsController.createGroup(groupDto);
            return ResponseEntity.status(201).body(createdGroup);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Delete a group", description = "Deletes a group by its ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted group"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer id) {
        try {
            GroupDto deleted = groupsController.deleteGroup(id);
            if (deleted != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}