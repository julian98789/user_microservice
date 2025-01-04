package com.user_microservice.user.infrastructure.input.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.user_microservice.user.application.dto.user_dto.UserRequest;
import com.user_microservice.user.application.dto.user_dto.UserResponse;
import com.user_microservice.user.application.handler.user_handler.IUserHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUserHandler userHandler;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Registrar usuario con datos válidos debe devolver el usuario registrado")
    void registerUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Juana");
        userRequest.setLastName("Lopez");
        userRequest.setIdentification("123456789");
        userRequest.setDateOfBirth(LocalDate.of(2000, 5, 15));
        userRequest.setPhoneNumber("+573001234567");
        userRequest.setEmail("juana.lopez@example.com");
        userRequest.setPassword("password123");
        userRequest.setRole("ADMIN");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("Juana");
        userResponse.setLastName("Lopez");
        userResponse.setIdentification("123456789");
        userResponse.setPhoneNumber("+573001234567");
        userResponse.setDateOfBirth(LocalDate.of(2000, 5, 15));
        userResponse.setEmail("juana.lopez@example.com");

        when(userHandler.registerUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/user/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(userResponse)));
    }
}
