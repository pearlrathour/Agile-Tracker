package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.dto.EpicDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.repositories.EpicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EpicService {
    private final EpicRepository epicRepository;

    public EpicService(EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
    }

    public EpicEntity createEpic(EpicDTO dto) {
        EpicEntity epic = EpicEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdById(dto.getCreatedById())
                .status("OPEN")
                .storyIds(new ArrayList<>())
                .build();
        return epicRepository.save(epic);
    }

    public List<EpicEntity> getAllEpics() {
        return epicRepository.findAll();
    }

    public Optional<EpicEntity> getEpicById(String id) {
        return epicRepository.findById(id);
    }

    public EpicEntity closeEpic(String id) {
        EpicEntity epic = epicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Epic not found"));
        epic.setStatus("CLOSED");
        return epicRepository.save(epic);
    }
}
