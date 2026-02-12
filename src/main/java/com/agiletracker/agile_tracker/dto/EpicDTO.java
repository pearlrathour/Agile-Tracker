package com.agiletracker.agile_tracker.dto;

import com.agiletracker.agile_tracker.Status.EpicStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpicDTO {
    private String id;
    private String title;
    private String description;
    private String createdById;
    private EpicStatus status; //TODO, IN_PROGRESS, DONE
}
