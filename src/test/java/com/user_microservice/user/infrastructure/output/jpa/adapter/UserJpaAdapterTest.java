package com.user_microservice.user.infrastructure.output.jpa.adapter;

import com.user_microservice.user.domain.model.UserModel;
import com.user_microservice.user.infrastructure.output.jpa.entity.UserEntity;
import com.user_microservice.user.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.user_microservice.user.infrastructure.output.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Register user should save and return UserModel")
    void registerUser() {
        UserModel userModel = new UserModel(1L, null, "password", "julian@mail.com", LocalDate.of(2000, 1, 1), "123456789", "ID123", "Doe", "John");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setPassword("encodedPassword");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userEntityMapper.userModelToUserEntity(userModel)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userEntityMapper.userEntityToUserModel(userEntity)).thenReturn(userModel);

        UserModel result = userJpaAdapter.registerUser(userModel);

        assertNotNull(result);
        assertEquals(userModel, result);
        verify(passwordEncoder, times(1)).encode("password");
        verify(userEntityMapper, times(1)).userModelToUserEntity(userModel);
        verify(userRepository, times(1)).save(userEntity);
        verify(userEntityMapper, times(1)).userEntityToUserModel(userEntity);
    }

    @Test
    @DisplayName("Exists user by email should return true when user exists")
    void existsUserByEmail() {
        String email = "julian@mail.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new UserEntity()));

        boolean result = userJpaAdapter.existsUserByEmail(email);

        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Exists user by email should return false when user does not exist")
    void existsUserByEmail_NotFound() {
        String email = "julian@mail.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = userJpaAdapter.existsUserByEmail(email);

        assertFalse(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Exists user by identification should return true when user exists")
    void existsUserByIdentification() {
        String identification = "ID123";

        when(userRepository.findByIdentification(identification)).thenReturn(Optional.of(new UserEntity()));

        boolean result = userJpaAdapter.existsUserByIdentification(identification);

        assertTrue(result);
        verify(userRepository, times(1)).findByIdentification(identification);
    }

    @Test
    @DisplayName("Exists user by identification should return false when user does not exist")
    void existsUserByIdentification_NotFound() {
        String identification = "ID123";

        when(userRepository.findByIdentification(identification)).thenReturn(Optional.empty());

        boolean result = userJpaAdapter.existsUserByIdentification(identification);

        assertFalse(result);
        verify(userRepository, times(1)).findByIdentification(identification);
    }
}