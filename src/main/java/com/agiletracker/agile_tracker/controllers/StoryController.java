package com.agiletracker.agile_tracker.controllers;

import com.agiletracker.agile_tracker.dto.StoryDTO;
import com.agiletracker.agile_tracker.entities.StoryEntity;
import com.agiletracker.agile_tracker.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    // Create
    @PostMapping
    public ResponseEntity<?> createStory(@RequestBody StoryDTO dto) {
        return ResponseEntity.ok(storyService.createStory(dto));
    }

    // Get all
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
