package com.agiletracker.agile_tracker.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private int storyPoints; // e.g., 1,2,3,5,8,13
    private String status;    // TODO, IN_PROGRESS, REVIEW, DONE
}
