package com.vm.xyz.app.api.v1;

import com.vm.xyz.app.model.JwtToken;
import com.vm.xyz.app.model.UsernameAndPasswordAuthRequest;
import com.vm.xyz.app.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("${api.v1}/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public JwtToken authentication(@RequestBody UsernameAndPasswordAuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return new JwtToken(jwtUtil.createToken(authentication));
    }
}
