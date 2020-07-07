package com.vm.xyz.app.security.filter;

import com.google.common.base.Strings;
import com.vm.xyz.app.configuration.JwtConfiguration;
import com.vm.xyz.app.exception.UnauthorizedException;
import com.vm.xyz.app.exception.XyzException;
import com.vm.xyz.app.util.ResponseMaker;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfiguration jwtConfiguration;
    private final ResponseMaker responseMaker;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(jwtConfiguration.getAuthorizationHeader());
        if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.replace(jwtConfiguration.getTokenPrefix(), "");
        Claims claims = validateToken(token, response);
        String username = claims.getSubject();
        List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("authorities");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                .collect(Collectors.toSet());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    public Claims validateToken(String token, HttpServletResponse response) throws IOException {
        String errorMessage = "Unexpected error.";
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtConfiguration.getSecretKey())
                    .build().parseClaimsJws(token).getBody();
        }catch (MalformedJwtException e){
            errorMessage = "Token is invalid.";
        }catch (UnsupportedJwtException e){
            errorMessage = "Token is unsupported.";
        }catch (ExpiredJwtException e){
            errorMessage = "Token is expired.";
        }catch (IllegalArgumentException e){
            errorMessage = "Token is empty.";
        }catch (SignatureException e){
            errorMessage = "Token signature is invalid.";
        }
        XyzException exception = new XyzException(errorMessage, HttpStatus.UNAUTHORIZED);
        responseMaker.make(exception, response, HttpServletResponse.SC_UNAUTHORIZED);
        throw new UnauthorizedException(errorMessage);
    }
}
