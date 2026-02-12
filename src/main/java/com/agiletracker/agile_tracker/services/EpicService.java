package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.Status.EpicStatus;
import com.agiletracker.agile_tracker.dto.EpicDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.repositories.EpicRepository;
import com.agiletracker.agile_tracker.repositories.UserRepository;
import com.agiletracker.agile_tracker.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EpicService {
    private final EpicRepository epicRepository;
    private final Validator userValidator;

    public Optional<EpicEntity> createEpic(EpicDTO dto) {
        try {
            userValidator.validateUserId(dto.getCreatedById(), "createdById");

            log.info("Creating new epic with title: {}", dto.getTitle());
            EpicEntity epic = EpicEntity.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .createdById(dto.getCreatedById())
                    .status(dto.getStatus() != null ? dto.getStatus() : EpicStatus.TODO)
                    .build();

            EpicEntity savedEpic = epicRepository.save(epic);
            log.info("Epic created successfully with id: {}", savedEpic.getId());
            return Optional.of(savedEpic);
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

    public Optional<EpicEntity> updateEpic(String id, EpicDTO dto) {
        try {
            log.info("Updating epic with ID: {}", id);
            return epicRepository.findById(id)
                    .map(existing -> {
                        if (dto.getTitle() != null) {
                            existing.setTitle(dto.getTitle());
                        }
                        if (dto.getDescription() != null) {
                            existing.setDescription(dto.getDescription());
                        }
                        if (dto.getCreatedById() != null) {
                            userValidator.validateUserId(dto.getCreatedById(), "createdById");
                            existing.setCreatedById(dto.getCreatedById());
                        }
                        if (dto.getStatus() != null) {
                            existing.setStatus(dto.getStatus());
                        }
                        EpicEntity updated = epicRepository.save(existing);
                        log.info("Epic updated successfully with id: {}", updated.getId());
                        return updated;
                    });
        } catch (Exception e) {
            log.error("Error updating epic with ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public boolean deleteEpic(String id) {
        try {
            log.info("Deleting epic with ID: {}", id);
            if (epicRepository.existsById(id)) {
                epicRepository.deleteById(id);
                log.info("Epic deleted successfully with ID: {}", id);
                return true;
            } else {
                log.warn("Epic with ID {} not found for deletion.", id);
                return false;
            }
        } catch (Exception e) {
            log.error("Error deleting epic with ID {}: {}", id, e.getMessage(), e);
            return false;
        }
    }
}
