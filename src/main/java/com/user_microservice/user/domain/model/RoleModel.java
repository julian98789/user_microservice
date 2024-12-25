package com.user_microservice.user.domain.model;

public class RoleModel {
    private Long id;
    private RoleName name;
    private String description;

    public enum RoleName {
        ADMIN,
        AUX_BODEGA,
        CLIENT
    }

    public RoleModel(Long id, RoleName name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
