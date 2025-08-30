package com.agiletracker.agile_tracker.services;

import com.agiletracker.agile_tracker.dto.UserDTO;
import com.agiletracker.agile_tracker.entities.UserEntity;
import com.agiletracker.agile_tracker.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity entity = UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();

        UserEntity user = userRepository.save(entity);
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .orElse(null);
    }
}
