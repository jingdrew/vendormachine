package com.vm.xyz.app.configuration;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class JwtConfiguration {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${app.jwt.token.expire}")
    private int expireTime;

    @Bean
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
