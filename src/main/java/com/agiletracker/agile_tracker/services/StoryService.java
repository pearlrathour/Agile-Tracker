package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.Status.StoryStatus;
import com.agiletracker.agile_tracker.dto.StoryDTO;
import com.agiletracker.agile_tracker.entities.StoryEntity;
import com.agiletracker.agile_tracker.repositories.StoryRepository;
import com.agiletracker.agile_tracker.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final Validator storyValidator;

    public Optional<StoryEntity> createStory(StoryDTO dto) {
        try{
            storyValidator.validateEpicId(dto.getEpicId());
            storyValidator.validateUserId(dto.getAssigneeId(), "assigneeId");
            storyValidator.validateUserId(dto.getReporterId(), "reporterId");

            StoryEntity story = StoryEntity.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .epicId(dto.getEpicId())
                    .assigneeId(dto.getAssigneeId())
                    .reporterId(dto.getReporterId())
                    .storyPoints(dto.getStoryPoints())
                    .status(dto.getStatus() != null ? dto.getStatus() : StoryStatus.TO_BE_REFINED)
                    .createdAt(Instant.now())
                    .build();

            StoryEntity saved = storyRepository.save(story);
            return Optional.of(saved);
        } catch (Exception e) {
            log.error("Failed to create story: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    public List<StoryEntity> getAllStories() {
        try {
            return storyRepository.findAll();
        } catch (Exception e) {
            log.error("Failed to fetch stories: {}", e.getMessage(), e);
            return List.of();
        }
    }

    public Optional<StoryEntity> getStoryById(String id) {
        try {
            log.info("Fetching story by ID: {}", id);
            return storyRepository.findById(id);
        } catch (Exception e) {
            log.error("Error fetching story by ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<StoryEntity> updateStory(String id, StoryDTO dto) {
        try {
            log.info("Updating story with ID: {}", id);
            return storyRepository.findById(id)
                    .map(existing -> {
                        if (dto.getTitle() != null) {
                            existing.setTitle(dto.getTitle());
                        }
                        if (dto.getDescription() != null) {
                            existing.setDescription(dto.getDescription());
                        }
                        if (dto.getEpicId() != null) {
                            storyValidator.validateEpicId(dto.getEpicId());
                            existing.setEpicId(dto.getEpicId());
                        }
                        if (dto.getAssigneeId() != null) {
                            storyValidator.validateUserId(dto.getAssigneeId(), "assigneeId");
                            existing.setAssigneeId(dto.getAssigneeId());
                        }
                        if (dto.getReporterId() != null) {
                            storyValidator.validateUserId(dto.getReporterId(), "reporterId");
                            existing.setReporterId(dto.getReporterId());
                        }
                        if (dto.getStoryPoints() != null) {
                            existing.setStoryPoints(dto.getStoryPoints());
                        }
                        if (dto.getStatus() != null) {
                            existing.setStatus(dto.getStatus());
                            if (dto.getStatus().equals(StoryStatus.DONE) ||
                                    dto.getStatus().equals(StoryStatus.DEPLOYED_BUT_NOT_COMPLETE)) {
                                existing.setClosedAt(Instant.now());
                            }
                        }
                        if (dto.getCreatedAt() != null) {
                            existing.setCreatedAt(dto.getCreatedAt());
                        }
                        if (dto.getClosedAt() != null) {
                            existing.setClosedAt(dto.getClosedAt());
                        }
                        StoryEntity updated = storyRepository.save(existing);
                        log.info("Story updated successfully with id: {}", updated.getStoryId());
                        return updated;
                    });
        } catch (Exception e) {
            log.error("Error updating story with ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public boolean deleteStory(String id) {
        try {
            log.info("Deleting story with ID: {}", id);
            if (storyRepository.existsById(id)) {
                storyRepository.deleteById(id);
                log.info("Story deleted successfully with ID: {}", id);
                return true;
            } else {
                log.warn("Story with ID {} not found for deletion.", id);
                return false;
            }
        } catch (Exception e) {
            log.error("Error deleting story with ID {}: {}", id, e.getMessage(), e);
            return false;
        }
    }
}
