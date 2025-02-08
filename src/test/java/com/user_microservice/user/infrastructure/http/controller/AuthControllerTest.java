package com.user_microservice.user.infrastructure.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_microservice.user.application.dto.authenticationdto.AuthenticationRequest;
import com.user_microservice.user.application.dto.authenticationdto.AuthenticationResponse;
import com.user_microservice.user.application.handler.jwthandler.IJwtHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IJwtHandler jwtHandler;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();

        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("example@mail.com");
        authenticationRequest.setPassword("password");

        authenticationResponse = new AuthenticationResponse("jwtToken");

    }


    @Test
    @DisplayName("Given valid credentials, when login, then return JWT token")
    void givenValidCredentials_whenLogin_thenReturnJwtToken() throws Exception {

        when(jwtHandler.login(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(authenticationResponse)));

        verify(jwtHandler, times(1)).login(any(AuthenticationRequest.class));
    }
}