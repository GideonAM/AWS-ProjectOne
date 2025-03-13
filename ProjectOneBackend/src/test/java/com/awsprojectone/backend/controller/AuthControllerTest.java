package com.awsprojectone.backend.controller;

import com.awsprojectone.backend.auth.AuthController;
import com.awsprojectone.backend.auth.AuthService;
import com.awsprojectone.backend.config.JwtService;
import com.awsprojectone.backend.dto.AuthDto;
import com.awsprojectone.backend.dto.ChangePasswordDto;
import com.awsprojectone.backend.dto.ForgotPasswordDto;
import com.awsprojectone.backend.dto.PasswordUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthDto authDto;

    @BeforeEach
    public void init() {
        authDto = AuthDto.builder()
                .email("user@gmail.com")
                .password("password")
                .build();
    }

    @Test
    public void AuthController_Register_ReturnString() throws Exception {
        given(authService
                .register(authDto)
        ).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void AuthController_Login_ReturnAuthResponse() throws Exception {

        given(authService.login(authDto)
        ).willAnswer( invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void AuthController_VerifyAccount_ReturnString() throws Exception {

        String response = "Account verified";
        String token = "token";
        when(authService.verifyAccount(token)).thenReturn(response);

        ResultActions results = mockMvc.perform(get("/auth/register/verify")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("token", token)
                .content(response));

        results.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void AuthController_ForgotPassword_ReturnString() throws Exception {

        ForgotPasswordDto forgotPasswordDto = ForgotPasswordDto.builder()
                .email("user@gmail.com")
                .build();

        given(authService
                .forgotPassword(forgotPasswordDto)
        ).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(post("/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotPasswordDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void AuthController_UpdatePassword_ReturnString() throws Exception {

        PasswordUpdateDto passwordUpdateDto = PasswordUpdateDto.builder()
                .password("password")
                .build();

        String token = "token";
        given(authService
                .updatePassword(token, passwordUpdateDto)
        ).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(post("/auth/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("token", token)
                .content(objectMapper.writeValueAsString(passwordUpdateDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void AuthController_ChangePassword_ReturnString() throws Exception {
        ChangePasswordDto changePasswordDto = ChangePasswordDto
                .builder()
                .currentPassword("password")
                .newPassword("passwords")
                .jwt("token")
                .build();

        given(authService
                .changePassword(changePasswordDto)
        ).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(post("/auth/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
