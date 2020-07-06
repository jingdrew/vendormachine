package com.vm.xyz.app.service.impl;

import com.vm.xyz.app.entity.XYZUser;
import com.vm.xyz.app.model.ApplicationUser;
import com.vm.xyz.app.model.ApplicationUserRole;
import com.vm.xyz.app.repository.XYZUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class XYZUserServiceDetailsImpl implements UserDetailsService {

    private final XYZUserRepository xyzUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        XYZUser xyzUser = xyzUserRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("Username " +username+" not found"));
        return new ApplicationUser(
                ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                xyzUser.getPassword(),
                xyzUser.getUsername(),
                true,
                true,
                true,
                true);
    }
}
