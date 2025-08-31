package com.agiletracker.agile_tracker.controllers;

import com.agiletracker.agile_tracker.dto.UserDTO;
import com.agiletracker.agile_tracker.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Optional<UserDTO> createUser(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }
}
