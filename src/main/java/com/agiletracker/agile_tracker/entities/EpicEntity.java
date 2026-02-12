package com.agiletracker.agile_tracker.entities;

import com.agiletracker.agile_tracker.Status.EpicStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "epics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpicEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String createdById;
    private EpicStatus status; // TODO, IN_PROGRESS, DONE
}

