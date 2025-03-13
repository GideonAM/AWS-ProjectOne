package com.awsprojectone.backend.auth;

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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SendMails sendMails;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final UserTokenRepository userTokenRepository;

    @Override
    @Transactional
    public String register(AuthDto registerDto) throws UserAlreadyRegisteredException {

        Optional<UserEntity> fetchedUser = userRepository.findByEmail(registerDto.getEmail());
        if (fetchedUser.isPresent())
            throw new UserAlreadyRegisteredException("User is already registered");

        UserEntity new_user = UserEntity.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.USER)
                .build();

        UserEntity savedUser = userRepository.save(new_user);
        sendMails.sendVerificationEmail(savedUser);
        return "Account verification link sent to email.";
    }

    @Override
    public AuthResponse login(AuthDto loginDto) {

        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).get();
        String jwt = jwtService.generateJwt(user);
        saveUserWithToken(jwt, user);

        return AuthResponse.builder()
                .userEmail(user.getEmail())
                .userId(user.getId())
                .jwt(jwt)
                .isAdmin(user.getRole().toString().equals("ADMIN"))
                .build();
    }

    private void saveUserWithToken(String jwt, UserEntity user) {
        UserToken userToken = UserToken.builder()
                                .token(jwt)
                                .user(user)
                                .build();
        userTokenRepository.save(userToken);
    }

    @Override
    public String verifyAccount(String token) {
        Optional<UserToken> verifyToken = userTokenRepository.findByToken(token);

        if (verifyToken.isPresent()) {
            UserEntity user = verifyToken.get().getUser();
            user.setEnabled(true);
            userRepository.save(user);
            return "Account verification successful";
        }

        return "Account verification failed";
    }

    @Override
    @Transactional
    public String forgotPassword(ForgotPasswordDto forgotPasswordDto) throws UserNotFound {

        UserEntity user = userRepository.findByEmail(forgotPasswordDto.getEmail()).orElseThrow(
                () -> new UserNotFound("User not found")
        );

        sendMails.sendPasswordResetEmail(user);
        return "Password reset link sent to email";
    }

    @Override
    public String updatePassword(String token, PasswordUpdateDto updatePasswordDto) throws UserNotFound {

        UserToken userToken = userTokenRepository.findByToken(token).orElseThrow(
                () -> new UserNotFound("Password update failed")
        );

        UserEntity user = userToken.getUser();
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getPassword()));
        userRepository.save(user);
        userTokenRepository.delete(userToken);
        return "Password updated successfully";
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) throws UserNotFound {

        UserToken userToken = userTokenRepository
                .findByToken(changePasswordDto.getJwt())
                .orElseThrow(() -> new UserNotFound("User not found"));

        UserEntity user = userToken.getUser();

        if (passwordEncoder.matches(changePasswordDto.getCurrentPassword(),
                user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));

            userRepository.save(user);
            return "Password updated successfully";
        }

        return "Incorrect current password";
    }

}
