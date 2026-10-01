package io.github.gcaixeta.hok.service;

import io.github.gcaixeta.hok.model.User;
import io.github.gcaixeta.hok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository users;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Integer saveUser(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        User savedUser = users.save(user);
        return savedUser.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User foundUser = users.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found for email: " + email));

        List<String> roles = foundUser.getRoles();

        Set<GrantedAuthority> ga = roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(email, foundUser.getPassword(), ga);
    }
}
