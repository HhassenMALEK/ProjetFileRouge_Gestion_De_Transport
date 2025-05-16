package com.api.ouimouve.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for User.
 * This class is used to transfer data between the application and the client.
 * It contains fields for the user's first name, last name, email, password, role, and license number.
 */
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String licenseNumber;
}
