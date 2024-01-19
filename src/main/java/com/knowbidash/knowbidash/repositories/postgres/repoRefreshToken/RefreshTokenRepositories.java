package com.knowbidash.knowbidash.repositories.postgres.repoRefreshToken;

import com.knowbidash.knowbidash.entities.postgres.refreshtoken.RefreshToken;
import com.knowbidash.knowbidash.entities.postgres.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepositories extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken findByUser(User user);
    @Modifying
    int deleteByUser(User user);
}
