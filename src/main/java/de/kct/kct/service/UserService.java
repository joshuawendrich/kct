package de.kct.kct.service;

import de.kct.kct.configuration.JwtService;
import de.kct.kct.dto.AuthDto;
import de.kct.kct.dto.LoginDto;
import de.kct.kct.dto.RegisterDto;
import de.kct.kct.entity.User;
import de.kct.kct.entity.UserKostenstelle;
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

    public AuthDto registerUser(RegisterDto registerDto) {
        if (registerDto.kostenstellen() == null || registerDto.kostenstellen().isEmpty())
            throw new IllegalStateException();
        String emailLowerCase = registerDto.email().toLowerCase();
        Optional<User> userWithEmail = userRepository.findByEmail(emailLowerCase);
        if (userWithEmail.isPresent()) throw new IllegalStateException();
        User user = new User();
        user.setEmail(emailLowerCase);
        user.setPasswordHash(passwordEncoder.encode(registerDto.password()));
        registerDto.kostenstellen().forEach(it -> {
            UserKostenstelle userKostenstelle = new UserKostenstelle();
            userKostenstelle.setKostenstelle(it);
            userKostenstelle.setUser(user);
            user.getKostenstellen().add(userKostenstelle);
        });
        userRepository.save(user);
        return new AuthDto(jwtService.generateToken(user));
    }
}
