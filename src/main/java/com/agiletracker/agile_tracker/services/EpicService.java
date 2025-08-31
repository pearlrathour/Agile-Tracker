package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.dto.EpicDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.repositories.EpicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EpicService {
    private final EpicRepository epicRepository;

    public EpicService(EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
    }

    public Optional<EpicEntity> createEpic(EpicDTO dto) {
        try {
            log.info("Creating new epic with title: {}", dto.getTitle());
            EpicEntity epic = EpicEntity.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .createdById(dto.getCreatedById())
                    .status("OPEN")
                    .storyIds(new ArrayList<>())
                    .build();

            EpicEntity savedEpic = epicRepository.save(epic);
            return Optional.of(epic);
        } catch (Exception e) {
            log.error("Error creating epic {}: {}", dto.getTitle(), e.getMessage(), e);
            return Optional.empty();
        }
    }

    public List<EpicEntity> getAllEpics() {
        try {
            log.info("Fetching all epics");
            return epicRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all epics: {}", e.getMessage(), e);
            return List.of();
        }
    }

    public Optional<EpicEntity> getEpicById(String id) {
        try {
            log.info("Fetching epic by ID: {}", id);
            return epicRepository.findById(id);
        } catch (Exception e) {
            log.error("Error fetching epic with ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<EpicEntity> closeEpic(String id) {
        try {
            log.info("Closing epic with ID: {}", id);
            return epicRepository.findById(id).map(epic -> {
                if ("CLOSED".equalsIgnoreCase(epic.getStatus())) {
                    log.warn("Epic {} is already CLOSED", id);
                    return epic;
                }
                epic.setStatus("CLOSED");
                EpicEntity closed = epicRepository.save(epic);
                return closed;
            });

        } catch (Exception e) {
            log.error("Error closing epic {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }
}
