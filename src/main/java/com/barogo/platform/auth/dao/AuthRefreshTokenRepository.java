package com.barogo.platform.auth.dao;

import com.barogo.platform.auth.Entity.AuthRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRefreshTokenRepository extends JpaRepository<AuthRefreshToken, String> {
    AuthRefreshToken findByUserId(String userId);
}
