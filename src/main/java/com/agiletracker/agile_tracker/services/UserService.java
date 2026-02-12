package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.dto.UserDTO;
import com.agiletracker.agile_tracker.entities.UserEntity;
import com.agiletracker.agile_tracker.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<UserDTO> createUser(UserDTO userDTO) {
        try {
            log.info("Creating user with email: {}", userDTO.getEmail());
            UserEntity entity = UserEntity.builder()
                    .name(userDTO.getName())
                    .email(userDTO.getEmail())
                    .role(userDTO.getRole())
                    .build();

            UserEntity user = userRepository.save(entity);
            return Optional.of(modelMapper.map(user, UserDTO.class));
        } catch (Exception e) {
            log.error("Error creating user with email {}: {}", userDTO.getEmail(), e.getMessage(), e);
            return Optional.empty();
        }
    }

    public List<UserDTO> getAllUsers() {
        try {
            log.info("Fetching all users");
            return userRepository
                    .findAll()
                    .stream()
                    .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all users: {}", e.getMessage(), e);
            return List.of();
        }
    }

    public Optional<UserDTO> getUserById(String id) {
        try {
            log.info("Fetching user by ID: {}", id);
            return userRepository.findById(id)
                    .map(userEntity -> {
                        return modelMapper.map(userEntity, UserDTO.class);
                    });

        } catch (Exception e) {
            log.error("Error fetching user by ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<UserDTO> updateUser(String id, UserDTO dto) {
        try {
            log.info("Updating user with ID: {}", id);
            return userRepository.findById(id)
                    .map(existing -> {
                        if (dto.getName() != null) {
                            existing.setName(dto.getName());
                        }
                        if (dto.getEmail() != null) {
                            existing.setEmail(dto.getEmail());
                        }
                        if (dto.getRole() != null) {
                            existing.setRole(dto.getRole());
                        }
                        UserEntity updated = userRepository.save(existing);
                        log.info("User updated successfully with id: {}", updated.getId());
                        return modelMapper.map(updated, UserDTO.class);
                    });
        } catch (Exception e) {
            log.error("Error updating user with ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public void deleteUser(String id) {
        try {
            log.info("Deleting user with ID: {}", id);
            userRepository.deleteById(id);
            log.info("User deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
        }
    }
}
