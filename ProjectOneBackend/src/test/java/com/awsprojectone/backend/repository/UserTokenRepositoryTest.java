package com.awsprojectone.backend.repository;

import com.awsprojectone.backend.auth.Role;
import com.awsprojectone.backend.entity.UserEntity;
import com.awsprojectone.backend.entity.UserToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserTokenRepositoryTest {

    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserTokenRepository_FindByToken_ReturnToken() {

        String token = UUID.randomUUID().toString();
        UserEntity user = UserEntity.builder()
                .email("user@gmail.com")
                .password("password")
                .role(Role.USER)
                .build();

        UserToken userToken = UserToken.builder()
                .token(token)
                .user(user)
                .build();

        userRepository.save(user);
        UserToken savedToken = userTokenRepository.save(userToken);

        Optional<UserToken> fetchedToken = userTokenRepository.findByToken(savedToken.getToken());

        Assertions.assertThat(fetchedToken.isPresent()).isEqualTo(true);
    }
}
