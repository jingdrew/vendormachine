package com.vm.xyz.app.service.impl;

import com.google.common.base.Strings;
import com.vm.xyz.app.entity.XYZUser;
import com.vm.xyz.app.exception.ForbiddenException;
import com.vm.xyz.app.exception.UnauthorizedException;
import com.vm.xyz.app.model.ApplicationUserRole;
import com.vm.xyz.app.repository.XYZUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class XYZUserServiceImpl implements AuthenticationProvider {

    private final XYZUserRepository xyzUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
            XYZUser xyzuser = xyzUserRepository.findByUsername(username)
                    .orElseThrow(() -> new UnauthorizedException("User does not exist."));
            if (passwordEncoder.matches(password, xyzuser.getPassword())) {
                if (isUserLockedOut(xyzuser)) {
                    throw new ForbiddenException("User is locked out, try again tomorrow.");
                } else {
                    return new UsernamePasswordAuthenticationToken(
                            xyzuser.getUsername(),
                            xyzuser.getPassword(),
                            ApplicationUserRole.ADMIN.getGrantedAuthorities());
                }
            } else {
                setUserLoginAttempts(xyzuser);
                throw new UnauthorizedException("Bad credentials, username and password did not match.");
            }
        } else {
            throw new UnauthorizedException("Invalid credentials.");
        }
    }

    private boolean isUserLockedOut(XYZUser xyzUser) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean isLocked = false;
        if (xyzUser.getLoginAttempts() >= 2) {
            if (sdf.format(new Date()).equals(sdf.format(xyzUser.getUpdated()))) {
                isLocked = true;
            } else {
                xyzUser.setLoginAttempts(0);
                xyzUserRepository.save(xyzUser);
                isLocked = false;
            }
        }
        return isLocked;
    }

    private void setUserLoginAttempts(XYZUser xyzUser) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (sdf.format(new Date()).equals(sdf.format(xyzUser.getUpdated()))) {
            xyzUser.setLoginAttempts(xyzUser.getLoginAttempts() + 1);
        } else {
            xyzUser.setLoginAttempts(1);
        }
        xyzUserRepository.save(xyzUser);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
