package com.api.ouimouve.service;

import com.api.ouimouve.dto.UserDto;
import com.api.ouimouve.dto.UserWithPasswordDto;
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
                .map(userMapper::toDtoWithoutPassword)
                .toList();
    }
    /**
     * Get a user by its ID
     * @param id the ID of the user
     * @return the UserDto if found, null otherwise
     */
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDtoWithoutPassword)
                .orElse(null);
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
            return userMapper
                    .toDtoWithoutPassword(userRepository
                            .save(userMapper.toEntity(userDto)));
        }
        return null;
    }

    /**
     * Update the password of an existing user
     * @param id the id of the user to update
     * @param user the UserWithPasswordDto to update
     * @return the updated user
     */
    public UserWithPasswordDto updateUserPassword(Long id, UserWithPasswordDto user) {
        UserWithPasswordDto existingUser = userMapper
                .toDtoWithPassword(userRepository
                        .findById(id).orElse(null));
        if (existingUser != null) {
            existingUser.setId(id);
            return userMapper.toDtoWithPassword(userRepository
                    .save(userMapper
                            .toEntityWithPassword(user)));
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

    public UserDto findUserByEmail(String email) {
        return userMapper.toDtoWithoutPassword(userRepository
                .findByEmail(email)
                .orElse(null));
    }

}
