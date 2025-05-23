package com.api.ouimouve.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for User with password.
 */
@Data
public class UserWithPasswordDto {
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String licenseNumber;
}
