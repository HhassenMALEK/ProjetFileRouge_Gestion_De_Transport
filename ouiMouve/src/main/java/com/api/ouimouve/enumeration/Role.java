package com.api.ouimouve.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration representing user roles in the application.
 * Possible roles include:
 * - USER
 * - ADMIN
 */
@Getter
@AllArgsConstructor
public enum Role {

    /**
     * Represents a standard user role.
     */
    USER("ROLE_USER"),

    /**
     * Represents an administrator role with elevated privileges.
     */
    ADMIN("ROLE_ADMIN");

    private final String role;

}
