package com.awsprojectone.backend.repository;

import com.awsprojectone.backend.auth.Role;
import com.awsprojectone.backend.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    public void init() {
        user = UserEntity.builder()
                .email("user@gmail.com")
                .password("password")
                .role(Role.USER)
                .build();
    }

    @Test
    public void UserRepository_Save_ReturnSavedUser() {

        UserEntity savedUser = userRepository.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotBlank();
    }

    @Test
    public void UserRepository_FindByEmail_ReturnUser() {

        userRepository.save(user);
        Optional<UserEntity> fetchedUser = userRepository.findByEmail(user.getEmail());

        Assertions.assertThat(fetchedUser.isPresent()).isEqualTo(true);
    }
}
