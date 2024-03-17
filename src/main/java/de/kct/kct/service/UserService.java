package de.kct.kct.service;

import de.kct.kct.configuration.JwtService;
import de.kct.kct.dto.AuthDto;
import de.kct.kct.dto.LoginDto;
import de.kct.kct.entity.User;
import de.kct.kct.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthDto loginUser(LoginDto loginDto) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail().toLowerCase(), loginDto.getPassword()));
        return new AuthDto(jwtService.generateToken((UserDetails) auth.getPrincipal()));
    }

    public AuthDto registerUser(LoginDto registerDto) {
        String emailLowerCase = registerDto.getEmail().toLowerCase();
        Optional<User> userWithEmail = userRepository.findByEmail(emailLowerCase);
        if (userWithEmail.isPresent()) throw new IllegalStateException();
        User user = new User();
        user.setEmail(emailLowerCase);
        user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        return new AuthDto(jwtService.generateToken(user));
    }
}
