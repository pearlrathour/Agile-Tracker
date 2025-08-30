package com.agiletracker.agile_tracker.repositories;

import com.agiletracker.agile_tracker.entities.StoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends MongoRepository<StoryEntity, String> {
    List<StoryEntity> findByEpicId(String epicId);
}
