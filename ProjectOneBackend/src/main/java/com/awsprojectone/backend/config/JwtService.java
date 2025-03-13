package com.awsprojectone.backend.config;

import com.awsprojectone.backend.entity.UserEntity;
import com.awsprojectone.backend.repository.UserTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.application.secret.key}")
    private String key;

    private final SecretKey SECRET_KEY;
    private final UserTokenRepository userTokenRepository;

    public String getUserEmail(String jwt) {
        return extractClaims(jwt, Claims::getSubject);
    }

    private <T> T extractClaims(String jwt, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(jwt);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isValidToken(UserDetails userDetails, String jwt) {
        String userEmail = userDetails.getUsername();
        String retrievedEmail = extractClaims(jwt, Claims::getSubject);
        Date date = extractClaims(jwt, Claims::getExpiration);
        boolean tokenPresent = userTokenRepository.findByToken(jwt).isPresent();

        return date.after(new Date())
               && userEmail.equals(retrievedEmail)
                && tokenPresent;
    }

    public String generateJwt(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        SECRET_KEY.setSecretKey(key);
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY.getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }
}
