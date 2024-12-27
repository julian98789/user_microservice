package com.user_microservice.user.application.dto.role_dto.role_dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class RoleResponse {
    private Long id;
    private String name;

    private String description;

}
