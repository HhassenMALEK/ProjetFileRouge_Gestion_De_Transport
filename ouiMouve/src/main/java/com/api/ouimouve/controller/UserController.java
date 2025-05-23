package com.api.ouimouve.controller;

import com.api.ouimouve.dto.UserDto;
import com.api.ouimouve.exception.InvalidRessourceException;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController is a REST controller that handles user-related operations.
 * It provides endpoints to create, read, update, and delete users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Get all users
     * @return a list of UserDto
     */
    @GetMapping("/all")
    public List<UserDto> getAllUsers() throws RessourceNotFoundException {
        try {
            return userService.getAllUsers();
        } catch (RessourceNotFoundException e) {
            throw new RessourceNotFoundException(e.getMessage());
        }
    }

    /**
     * Get a user by its ID
     * @param id the ID of the user
     * @return the UserDto if found, null otherwise
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws RessourceNotFoundException {
        UserDto user =  userService.getUserById(id);
        if (user == null) {
            throw new RessourceNotFoundException("User not found with id: " + id);
        }
        return user;
    }

    /**
     * Update an existing user
     * @param id the id of the user to update
     * @param userDto the UserDto with updated information
     * @return the updated UserDto
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws InvalidRessourceException {
        try {
            return userService.updateUser(id, userDto);
        } catch (InvalidRessourceException e) {
            throw new InvalidRessourceException(e.getMessage());
        }
    }

    /**
     * Delete a user by its ID
     * @param id the ID of the user to delete
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws RessourceNotFoundException {
        UserDto user = userService.getUserById(id);
        if (user == null) {
            throw new RessourceNotFoundException("User not found with id: " + id);
        }
        userService.deleteUser(id);
    }


}
