package com.user_microservice.user.application.user_dto;

import com.user_microservice.user.application.role_dto.RoleResponse;

import java.time.LocalDate;

public class UserResponse {
    private String name;
    private String lastName;

    private String identification;

    private String  phone;

    private LocalDate dateOfBirth;

    private String email;

    private RoleResponse role;
}
