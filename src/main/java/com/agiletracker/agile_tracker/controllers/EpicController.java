package com.agiletracker.agile_tracker.controllers;

import com.agiletracker.agile_tracker.dto.EpicDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.services.EpicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Epics", description = "Endpoints for managing epics")
@RestController
@RequestMapping("/epics")
@RequiredArgsConstructor
public class EpicController {

    private final EpicService epicService;

    @Operation(
        summary = "Create a new epic",
        description = "Creates a new epic with the provided details."
    )
    @PostMapping
    public ResponseEntity<?> createEpic(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Epic details",
            required = true
        )
        @RequestBody EpicDTO dto) {
        return ResponseEntity.ok(epicService.createEpic(dto));
    }

    @Operation(
        summary = "Get all epics",
        description = "Returns a list of all epics in the system."
    )
    @GetMapping
    public ResponseEntity<List<EpicEntity>> getAllEpics() {
        return ResponseEntity.ok(epicService.getAllEpics());
    }

    @Operation(
        summary = "Get epic by ID",
        description = "Fetches the epic with the specified ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<EpicEntity> getEpicById(
        @Parameter(description = "ID of the epic to fetch", example = "epic123")
        @PathVariable String id) {
        return epicService.getEpicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Close an epic",
        description = "Closes the epic with the specified ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> closeEpic(
        @Parameter(description = "ID of the epic to close", example = "epic123")
        @PathVariable String id) {
        return ResponseEntity.ok(epicService.closeEpic(id));
    }
}