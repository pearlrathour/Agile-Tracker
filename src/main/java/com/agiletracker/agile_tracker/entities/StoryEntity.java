package com.agiletracker.agile_tracker.entities;

import com.agiletracker.agile_tracker.Status.StoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "stories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryEntity {
    @Id
    private String storyId;
    private String title;
    private String description;
    private String epicId;
    private String assigneeId; // Developer
    private String reporterId;
    private Integer storyPoints;   // Should be 1, 2, 5, etc.
    private StoryStatus status;
    private Instant createdAt;
    private Instant closedAt;
}
