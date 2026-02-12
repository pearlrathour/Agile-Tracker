package com.agiletracker.agile_tracker.validation;

import com.agiletracker.agile_tracker.repositories.EpicRepository;
import com.agiletracker.agile_tracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final EpicRepository epicRepository;
    private final UserRepository userRepository;

    public void validateEpicId(String epicId) {
        if (epicId == null || !epicRepository.existsById(epicId)) {
            throw new IllegalArgumentException("epicId must be a valid epic ID");
        }
    }

    public void validateUserId(String userId, String fieldName) {
        if (userId == null || !userRepository.existsById(userId)) {
            throw new IllegalArgumentException(fieldName + " must be a valid user ID");
        }
    }
}

