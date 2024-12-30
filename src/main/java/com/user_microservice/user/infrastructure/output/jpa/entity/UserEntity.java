package com.user_microservice.user.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "identification", nullable = false, length = 20)
    private String identification;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
