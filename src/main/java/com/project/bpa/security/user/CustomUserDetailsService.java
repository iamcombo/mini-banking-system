package com.project.bpa.security.user;

import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.authentication.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Return our CustomUserDetails so it becomes the Authentication principal
        return new CustomUserDetails(
            user.getUsername(),
            user.getPassword(),
            authorities,
            user.getId(),
            user.getEmail(),
            user
        );
    }
}
