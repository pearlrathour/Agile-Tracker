package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.dto.StoryDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.entities.StoryEntity;
import com.agiletracker.agile_tracker.repositories.EpicRepository;
import com.agiletracker.agile_tracker.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final EpicRepository epicRepository;

    public StoryService(StoryRepository storyRepository, EpicRepository epicRepository) {
        this.storyRepository = storyRepository;
        this.epicRepository = epicRepository;
    }

    // Create story
    public Optional<StoryEntity> createStory(StoryDTO dto) {
        try{
            EpicEntity epic = epicRepository.findById(dto.getEpicId())
                    .orElseThrow(() -> new RuntimeException("Epic not found"));

            StoryEntity story = StoryEntity.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .epicId(dto.getEpicId())
                    .assigneeId(dto.getAssigneeId())
                    .reporterId(dto.getReporterId())
                    .storyPoints(dto.getStoryPoints())
                    .status("TODO")
                    .build();

            StoryEntity saved = storyRepository.save(story);

            epic.getStoryIds().add(saved.getStoryId());
            epicRepository.save(epic);

            return Optional.of(saved);
        } catch (Exception e) {
            log.error("Failed to create story: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    // Get all stories
    public List<StoryEntity> getAllStories() {
        try {
            return storyRepository.findAll();
        } catch (Exception e) {
            log.error("Failed to fetch stories: {}", e.getMessage(), e);
            return List.of();
        }
    }

//    // Get stories by Epic
//    public List<StoryEntity> getStoriesByEpic(String epicId) {
//        return storyRepository.findByEpicId(epicId);
//    }

//    // Update status
//    public StoryEntity updateStatus(String storyId, String status) {
//        StoryEntity story = storyRepository.findById(storyId)
//                .orElseThrow(() -> new RuntimeException("Story not found"));
//        story.setStatus(status);
//        return storyRepository.save(story);
//    }
}
