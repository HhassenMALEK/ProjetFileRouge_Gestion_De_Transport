package com.api.ouimouve.service;

import com.api.ouimouve.dto.UserDto;
import com.api.ouimouve.mapper.UserMapper;
import com.api.ouimouve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing User entities.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    /**
     * Get all users
     * @return a list of UserDto
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
    /**
     * Get a user by its ID
     * @param id the ID of the user
     * @return the UserDto if found, null otherwise
     */
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }
    /**
     * Create a new user
     * @param userDto the UserDto to create
     * @return the created UserDto
     */
    public UserDto createUser(UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    /**
     * Update an existing user
     * @param id the id of the user to update
     * @param userDto the UserDto to update
     * @return the updated UserDto
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        UserDto existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setId(id);
            return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
        }
        return null;
    }

    /**
     * Delete a user by its ID
     * @param id the ID of the user to delete
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
