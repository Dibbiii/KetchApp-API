package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.FriendsControllers;
import com.alessandra_alessandro.ketchapp.models.dto.FriendDto;
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
@RequestMapping("/api/friends")
public class FriendsRoutes {
    private final FriendsControllers friendsController;

    @Autowired
    public FriendsRoutes(FriendsControllers friendsController) {
        this.friendsController = friendsController;
    }

    @Operation(summary = "Get all friends", description = "Fetches all friends.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved friends list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FriendDto.class))),
            @ApiResponse(responseCode = "404", description = "Friends list not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<FriendDto>> getFriends() {
        try {
            List<FriendDto> friends = friendsController.getFriends();
            return ResponseEntity.ok(friends);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get a friendship by Id", description = "Fetches a friendship record by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved friendship record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FriendDto.class))),
            @ApiResponse(responseCode = "404", description = "Friendship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FriendDto> getFriendship(@PathVariable Integer id) {
        try {
            FriendDto friend = friendsController.getFriendship(id);
            if (friend != null) {
                return ResponseEntity.ok(friend);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create a new friendship", description = "Creates a new friend record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created friendship record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FriendDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<FriendDto> createFriendship(FriendDto friendDtoToCreate) {
        try {
            FriendDto createdFriend = friendsController.createFriendship(friendDtoToCreate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFriend);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete a friendship by Id", description = "Deletes a friend record by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted friendship record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FriendDto.class))),
            @ApiResponse(responseCode = "404", description = "Friendship not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<FriendDto> deleteFriendship(@PathVariable Integer id) {
        try {
            FriendDto deletedFriendship = friendsController.deleteFriendship(id);
            if (deletedFriendship != null) {
                return ResponseEntity.ok(deletedFriendship);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
