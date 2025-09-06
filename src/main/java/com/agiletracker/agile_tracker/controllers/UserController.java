package com.agiletracker.agile_tracker.controllers;

import com.agiletracker.agile_tracker.dto.UserDTO;
import com.agiletracker.agile_tracker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Users", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        summary = "Create a new user",
        description = "Creates a new user in the system."
    )
    @PostMapping
    public Optional<UserDTO> createUser(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    @Operation(
        summary = "Get all users",
        description = "Returns a list of all users in the system."
    )
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
        summary = "Get user by ID",
        description = "Fetches the user with the specified ID."
    )
    @GetMapping("/{id}")
    public Optional<UserDTO> getUser(
        @Parameter(description = "ID of the user to fetch", example = "user123")
        @PathVariable String id) {
        return userService.getUserById(id);
    }
}