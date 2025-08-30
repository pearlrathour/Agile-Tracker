package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.dto.StoryDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.entities.StoryEntity;
import com.agiletracker.agile_tracker.repositories.EpicRepository;
import com.agiletracker.agile_tracker.repositories.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final EpicRepository epicRepository;

    public StoryService(StoryRepository storyRepository, EpicRepository epicRepository) {
        this.storyRepository = storyRepository;
        this.epicRepository = epicRepository;
    }

    // Create story
    public StoryEntity createStory(StoryDTO dto) {
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

        return saved;
    }

    // Get all stories
    public List<StoryEntity> getAllStories() {
        return storyRepository.findAll();
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
