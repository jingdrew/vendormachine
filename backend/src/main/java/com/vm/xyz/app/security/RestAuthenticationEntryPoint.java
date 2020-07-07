package com.vm.xyz.app.security;

import com.vm.xyz.app.exception.XyzException;
import com.vm.xyz.app.util.ResponseMaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ResponseMaker responseMaker;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        XyzException exception = new XyzException("Token is invalid.", HttpStatus.UNAUTHORIZED);
        responseMaker.make(exception, response, HttpServletResponse.SC_UNAUTHORIZED);
    }
}
