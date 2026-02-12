package com.agiletracker.agile_tracker.controllers;

import com.agiletracker.agile_tracker.dto.StoryDTO;
import com.agiletracker.agile_tracker.entities.StoryEntity;
import com.agiletracker.agile_tracker.services.StoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Stories", description = "Endpoints for managing stories")
@RestController
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    // Create
    @Operation(summary = "Create a new story", description = "Creates a new story and assigns it to an epic")
    @PostMapping
    public ResponseEntity<?> createStory(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Story details",
            required = true
        )
        @RequestBody StoryDTO dto) {
        return storyService.createStory(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // Get all
    @Operation(summary = "Get all stories", description = "Returns a list of all stories in the system")
    @GetMapping
    public ResponseEntity<List<StoryEntity>> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    @Operation(
            summary = "Get story by ID",
            description = "Fetches the story with the specified ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<StoryEntity> getStoryById(
            @Parameter(description = "ID of the story to fetch", example = "story123")
            @PathVariable String id) {
        return storyService.getStoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update a story by ID",
            description = "Updates the story with the specified ID. Only non-null fields will be updated."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStory(
            @Parameter(description = "ID of the story to update", example = "story123")
            @PathVariable String id,
            @RequestBody StoryDTO dto) {
        Optional<StoryEntity> updated = storyService.updateStory(id, dto);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a story by ID",
            description = "Deletes the story with the specified ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(
            @Parameter(description = "ID of the story to delete", example = "story123")
            @PathVariable String id) {
        boolean deleted = storyService.deleteStory(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}