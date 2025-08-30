package com.agiletracker.agile_tracker.repositories;

import com.agiletracker.agile_tracker.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
}
