package com.awsprojectone.backend.auth;

import com.awsprojectone.backend.entity.UserEntity;
import com.awsprojectone.backend.entity.UserToken;
import com.awsprojectone.backend.repository.FileRepository;
import com.awsprojectone.backend.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendMails {

//    @Value("${spring.mail.username}")
    private final String fileServerEmail = "admin@gmail.com";

    @Value("${file.server.base-url}")
    private String fileServerBaseUrl;

    private final FileRepository fileRepository;
    private final UserTokenRepository userTokenRepository;
    private final JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(SendMails.class);

    @Async
    public void sendVerificationEmail(UserEntity user) {
        String token = getUserToken(user);
        String mailSubject = "File Server Account Verification";
        String mailBody = """
                Account Verification,
                
                Kindly tap on the link below to verify your account
                
                %s
                """;

        String url = fileServerBaseUrl + "/auth/register/verify?token=" + token;

        try {
            sendMail(user.getEmail(), mailSubject, mailBody, url);
        } catch (Exception exception) {
            logger.error("Error processing request at /auth/login ", exception);
            throw new RuntimeException("Failed to send verification email, try again later");
        }
    }

    @Async
    public void sendPasswordResetEmail(UserEntity user) {
        String token = getUserToken(user);
        String mailSubject = "File Server Password Reset Link";
        String mailBody = """
                Password Reset,
                
                Kindly tap on the link below to reset your account password
                
                https://file-server-frontend.vercel.app/update-password/%s
                """;

        try {
            sendMail(user.getEmail(), mailSubject, mailBody, token);
        } catch (Exception exception) {
            logger.error("Error processing request at /auth/forgot-password ", exception);
            throw new RuntimeException("Failed to send password reset email, try again later");
        }
    }

    private String getUserToken(UserEntity user) {
        String token = UUID.randomUUID().toString();
        UserToken userToken = UserToken.builder()
                .token(token)
                .user(user)
                .build();

        userTokenRepository.save(userToken);
        return token;
    }

    private void sendMail(String userEmail, String mailSubject, String mailBody, String url) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(fileServerEmail);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject(mailSubject);
        String text = String.format(mailBody, url);
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }

}
