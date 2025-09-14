package com.vip; // It's good practice to put services in a 'service' package

import com.vip.User;
import com.vip.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service // Mark this as a Spring Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // --- THIS PART IS UPDATED ---
        // 1. Find the user in the database. This will return a User object or null.
        User user = userRepository.findByEmail(email);

        // 2. Check if the user was found. If not, throw an exception.
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 3. Return a Spring Security User object if the user was found.
        // We use Collections.emptyList() because we are not using roles.
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.emptyList()
        );
    }
}