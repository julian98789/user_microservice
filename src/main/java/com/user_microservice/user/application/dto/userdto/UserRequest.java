package com.user_microservice.user.application.dto.userdto;

import com.user_microservice.user.domain.util.Util;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserRequest {
    @NotBlank(message = Util.NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = Util.LAST_NAME_NOT_BLANK)
    private String lastName;

    @NotBlank(message = Util.IDENTIFICATION_NOT_BLANK)
    @Pattern(regexp = "^\\d+$", message = Util.IDENTIFICATION_PATTERN)
    private String  identification;

    @NotNull(message = Util.DATE_OF_BIRTH_NOT_NULL)
    private LocalDate dateOfBirth;

    @NotBlank(message = Util.PHONE_NOT_BLANK)
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = Util.PHONE_PATTERN)
    private String phoneNumber;

    @NotBlank(message = Util.EMAIL_NOT_BLANK)
    @Email(message = Util.EMAIL_PATTERN)
    private String email;

    @NotBlank(message = Util.PASSWORD_NOT_BLANK)
    private String password;

    @NotBlank(message = Util.NAME_ROLE_NOT_BLANK)
    private String role;

}
