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
import java.util.Optional;

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
        return epicService.createEpic(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
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
            summary = "Update an epic by ID",
            description = "Updates the epic with the specified ID. Only non-null fields will be updated."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEpic(
            @Parameter(description = "ID of the epic to update", example = "epic123")
            @PathVariable String id,
            @RequestBody EpicDTO dto) {
        Optional<EpicEntity> updated = epicService.updateEpic(id, dto);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete an epic by ID",
            description = "Deletes the epic with the specified ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpic(
            @Parameter(description = "ID of the epic to delete", example = "epic123")
            @PathVariable String id) {
        boolean deleted = epicService.deleteEpic(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}