package com.awsprojectone.backend.repository;

import com.awsprojectone.backend.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    Optional<UserToken> findByToken(String token);

    @Query(value = """
            select token from UserToken token
            inner join UserEntity userEntity on (userEntity.id = token.user.id)
            where userEntity.id = :userId
            """)
    List<UserToken> findAllTokensByUser(String userId);
}
