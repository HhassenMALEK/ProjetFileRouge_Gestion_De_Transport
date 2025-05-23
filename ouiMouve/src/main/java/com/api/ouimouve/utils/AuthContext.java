package com.api.ouimouve.utils;

import com.api.ouimouve.dto.UserDto;
import com.api.ouimouve.exception.RessourceNotFoundException;
import com.api.ouimouve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * AuthContext is a utility class that provides methods to get the current authenticated user.
 */
@Service
public class AuthContext {

    @Autowired
    private UserService userService;

    public UserDto getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            return userService.findUserByEmail(email);
        }
        throw new RessourceNotFoundException("You're not authenticated");
    }
}
