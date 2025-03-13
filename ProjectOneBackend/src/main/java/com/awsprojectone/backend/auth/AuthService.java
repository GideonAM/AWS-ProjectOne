package com.awsprojectone.backend.auth;

import com.awsprojectone.backend.dto.AuthDto;
import com.awsprojectone.backend.dto.ChangePasswordDto;
import com.awsprojectone.backend.dto.ForgotPasswordDto;
import com.awsprojectone.backend.dto.PasswordUpdateDto;
import com.awsprojectone.backend.exception.UserAlreadyRegisteredException;
import com.awsprojectone.backend.exception.UserNotFound;

public interface AuthService {

    String register(AuthDto registerDto) throws UserAlreadyRegisteredException;

    AuthResponse login(AuthDto loginDto);

    String verifyAccount(String token);

    String forgotPassword(ForgotPasswordDto forgotPasswordDto) throws UserNotFound;

    String updatePassword(String token, PasswordUpdateDto updatePasswordDto) throws UserNotFound;

    String changePassword(ChangePasswordDto changePasswordDto) throws UserNotFound;
}
