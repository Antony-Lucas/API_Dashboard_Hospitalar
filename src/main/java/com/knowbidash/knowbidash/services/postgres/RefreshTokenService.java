package com.knowbidash.knowbidash.services.postgres;

import com.knowbidash.knowbidash.entities.postgres.refreshtoken.RefreshToken;
import com.knowbidash.knowbidash.exceptions.TokenRefreshException;
import com.knowbidash.knowbidash.repositories.postgres.repoRefreshToken.RefreshTokenRepositories;
import com.knowbidash.knowbidash.repositories.postgres.repoUser.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.refreshExpirationDateinMS}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepositories refreshTokenRepositories;
    @Autowired
    private UserRepositories userRepositories;

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepositories.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId){
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepositories.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepositories.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepositories.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteyUserId(Long userId){
        return refreshTokenRepositories.deleteByUser(userRepositories.findById(userId).get());
    }
}
