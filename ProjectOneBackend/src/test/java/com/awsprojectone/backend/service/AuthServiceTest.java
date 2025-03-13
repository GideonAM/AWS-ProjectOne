package com.awsprojectone.backend.service;

import com.awsprojectone.backend.auth.AuthResponse;
import com.awsprojectone.backend.auth.AuthServiceImpl;
import com.awsprojectone.backend.auth.Role;
import com.awsprojectone.backend.auth.SendMails;
import com.awsprojectone.backend.config.JwtService;
import com.awsprojectone.backend.dto.AuthDto;
import com.awsprojectone.backend.dto.ChangePasswordDto;
import com.awsprojectone.backend.dto.ForgotPasswordDto;
import com.awsprojectone.backend.dto.PasswordUpdateDto;
import com.awsprojectone.backend.entity.UserEntity;
import com.awsprojectone.backend.entity.UserToken;
import com.awsprojectone.backend.exception.UserAlreadyRegisteredException;
import com.awsprojectone.backend.exception.UserNotFound;
import com.awsprojectone.backend.repository.UserRepository;
import com.awsprojectone.backend.repository.UserTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserTokenRepository userTokenRepository;

    @Mock
    private SendMails sendMails;

    private AuthDto authDto;
    private UserEntity user;
    private UserToken userToken;
    private String token;

    @BeforeEach
    public void init() {

        token = "token";
        authDto = AuthDto.builder()
                .email("user@gmail.com")
                .password("password")
                .build();

        user = UserEntity.builder()
                .email("user@gmail.com")
                .password("password")
                .role(Role.USER)
                .build();

        userToken = UserToken.builder()
                .user(user)
                .token(token)
                .build();
    }

    @Test
    public void AuthService_Register_ReturnString()
            throws UserAlreadyRegisteredException {

        when(userRepository
                .findByEmail(authDto.getEmail())
        ).thenReturn(Optional.empty());

        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder
                .encode(authDto.getPassword())
        ).thenReturn("password");

        String response = authService.register(authDto);

        Assertions.assertThat(response).isEqualTo("Account verification link sent to email.");
    }

    @Test
    public void AuthService_Login_ReturnObject() {

        Authentication authentication = mock(Authentication.class);

        when(userRepository
                .findByEmail(authDto.getEmail())
        ).thenReturn(Optional.ofNullable(user));

        when(authenticationProvider
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))
        ).thenReturn(authentication);

        assert user != null;
        when(jwtService.generateJwt(user)).thenReturn("jwt");

        AuthResponse response = authService.login(authDto);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void AuthService_VerifyAccount_ReturnString() {

        when(userTokenRepository
                .findByToken(token)
        ).thenReturn(Optional.ofNullable(userToken));

        String response = authService.verifyAccount(token);

        Assertions.assertThat(response).isEqualTo("Account verification successful");
    }

    @Test
    public void AuthService_ForgotPassword_ReturnString() throws UserNotFound {

        ForgotPasswordDto forgotPasswordDto = ForgotPasswordDto.builder()
                .email("user@gmail.com")
                .build();

        when(userRepository
                .findByEmail(forgotPasswordDto.getEmail())
        ).thenReturn(Optional.ofNullable(user));

        String response = authService.forgotPassword(forgotPasswordDto);

        Assertions.assertThat(response).isEqualTo("Password reset link sent to email");
    }

    @Test
    public void AuthService_UpdatedPassword_ReturnString() throws UserNotFound {

        PasswordUpdateDto passwordUpdateDto = PasswordUpdateDto.builder()
                        .password("password")
                                .build();

        when(userTokenRepository.findByToken(token)
        ).thenReturn(Optional.ofNullable(userToken));

        String response = authService.updatePassword(token, passwordUpdateDto);

        Assertions.assertThat(response).isEqualTo("Password updated successfully");
    }

    @Test
    public void AuthService_ChangePassword_ReturnString() throws UserNotFound {

        ChangePasswordDto changePasswordDto = ChangePasswordDto
                                    .builder()
                                    .currentPassword("password")
                                    .newPassword("passwords")
                                    .jwt("token")
                                    .build();

        when(userTokenRepository
                .findByToken(changePasswordDto.getJwt())
        ).thenReturn(Optional.ofNullable(userToken));

        when(passwordEncoder
                .matches(changePasswordDto.getCurrentPassword(), user.getPassword())
        ).thenReturn(true);

        String response = authService.changePassword(changePasswordDto);

        Assertions.assertThat(response).isEqualTo("Password updated successfully");
    }
}
