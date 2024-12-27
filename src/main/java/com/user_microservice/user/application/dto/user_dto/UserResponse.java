package com.user_microservice.user.application.dto.user_dto;

import com.user_microservice.user.application.dto.role_dto.role_dto.RoleResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserResponse {

    private String name;
    private String lastName;

    private String identification;

    private String  phoneNumber;

    private LocalDate dateOfBirth;

    private String email;

    private RoleResponse role;


}
