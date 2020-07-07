package com.vm.xyz.app.security.util;

import com.vm.xyz.app.configuration.JwtConfiguration;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtil {

    private final JwtConfiguration jwtConfiguration;

    public String createToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getExpireTime())))
                .signWith(jwtConfiguration.getSecretKey()).compact();
    }
}
