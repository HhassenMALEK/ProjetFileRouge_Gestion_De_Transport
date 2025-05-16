package com.api.ouimouve.dto;

import lombok.Data;

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
