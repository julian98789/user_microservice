package com.user_microservice.user.domain.model;

import java.time.LocalDate;

public class UserModel {
    private Long id;
    private String name;
    private String lastName;
    private String identification;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private RoleModel role;

    public UserModel(Long id, RoleModel role, String password, String email, LocalDate dateOfBirth, String phoneNumber,
                     String identification, String lastName, String name) {
        this.id = id;
        this.role = role;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.identification = identification;
        this.lastName = lastName;
        this.name = name;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
}
