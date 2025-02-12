package com.user_microservice.user.application.dto.roledto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleResponse {
    private Long id;
    private String name;

    private String description;

}
