package com.agiletracker.agile_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int storyPoints;
}