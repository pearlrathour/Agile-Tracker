package com.agiletracker.agile_tracker.dto;

import com.agiletracker.agile_tracker.Status.StoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {
    private String StoryId;
    private String title;
    private String description;
    private String epicId;
    private String assigneeId;
    private String reporterId;
    private Integer storyPoints;   // Should be 1, 2, 5, etc.
    private StoryStatus status;         // to be refined, ready for development, in build, in refine, ready for deployment, done, deployed but not complete, rejected
    private Instant createdAt;
    private Instant closedAt;
}