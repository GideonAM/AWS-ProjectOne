package com.awsprojectone.backend.auth;

import com.awsprojectone.backend.dto.AuthDto;
import com.awsprojectone.backend.dto.ChangePasswordDto;
import com.awsprojectone.backend.dto.ForgotPasswordDto;
import com.awsprojectone.backend.dto.PasswordUpdateDto;
import com.awsprojectone.backend.exception.UserAlreadyRegisteredException;
import com.awsprojectone.backend.exception.UserNotFound;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthDto registerDto)
            throws UserAlreadyRegisteredException
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @GetMapping("/register/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        return ResponseEntity.ok(authService.verifyAccount(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto)
            throws UserNotFound
    {
        return ResponseEntity.ok(authService.forgotPassword(forgotPasswordDto));
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam("token") String token,
                                                 @RequestBody @Valid PasswordUpdateDto updatePasswordDto)
            throws UserNotFound {
        return ResponseEntity.ok(authService.updatePassword(token, updatePasswordDto));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto)
            throws UserNotFound {
        return ResponseEntity.ok(authService.changePassword(changePasswordDto));
    }

}
