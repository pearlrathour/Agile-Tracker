package com.agiletracker.agile_tracker.controllers;

import com.agiletracker.agile_tracker.dto.EpicDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.services.EpicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epics")
@RequiredArgsConstructor
public class EpicController {

    private final EpicService epicService;

    @PostMapping
    public ResponseEntity<EpicEntity> createEpic(@RequestBody EpicDTO dto) {
        return ResponseEntity.ok(epicService.createEpic(dto));
    }

    @GetMapping
    public ResponseEntity<List<EpicEntity>> getAllEpics() {
        return ResponseEntity.ok(epicService.getAllEpics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpicEntity> getEpicById(@PathVariable String id) {
        return epicService.getEpicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpicEntity> closeEpic(@PathVariable String id) {
        return ResponseEntity.ok(epicService.closeEpic(id));
    }
}
