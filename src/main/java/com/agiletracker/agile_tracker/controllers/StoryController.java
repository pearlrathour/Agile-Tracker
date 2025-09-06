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
        return ResponseEntity.ok(storyService.createStory(dto));
    }

    // Get all
    @Operation(summary = "Get all stories", description = "Returns a list of all stories in the system")
    @GetMapping
    public ResponseEntity<List<StoryEntity>> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

//    // Get by epic
//    @GetMapping("/epic/{epicId}")
//    public ResponseEntity<List<StoryEntity>> getStoriesByEpic(@PathVariable String epicId) {
//        return ResponseEntity.ok(storyService.getStoriesByEpic(epicId));
//    }

//    // Update status
//    @PutMapping("/{storyId}/status/{status}")
//    public ResponseEntity<StoryEntity> updateStatus(@PathVariable String storyId,
//                                                    @PathVariable String status) {
//        return ResponseEntity.ok(storyService.updateStatus(storyId, status));
//    }
}