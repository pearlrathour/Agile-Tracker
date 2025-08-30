package com.agiletracker.agile_tracker.repositories;

import com.agiletracker.agile_tracker.entities.EpicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpicRepository extends MongoRepository<EpicEntity, String> {
}

