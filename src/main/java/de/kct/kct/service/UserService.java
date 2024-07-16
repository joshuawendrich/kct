package de.kct.kct.service;

import de.kct.kct.configuration.JwtService;
import de.kct.kct.dto.AuthDto;
import de.kct.kct.dto.LoginDto;
import de.kct.kct.dto.RegisterDto;
import de.kct.kct.entity.User;
import de.kct.kct.entity.UserKostenstelle;
import de.kct.kct.repository.DatensatzRepository;
import de.kct.kct.repository.UserKostenstelleRepository;
import de.kct.kct.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserKostenstelleRepository userKostenstelleRepository;

    private final DatensatzRepository datensatzRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final DatenService datenService;

    public AuthDto loginUser(LoginDto loginDto) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail().toLowerCase(), loginDto.getPassword()));
        var userKostenstellen = userKostenstelleRepository.findByUser((User) auth.getPrincipal());
        var organisationseinheiten = datenService.getOrganisationseinheitenForUser((User) auth.getPrincipal());
        return new AuthDto(jwtService.generateToken((UserDetails) auth.getPrincipal()), userKostenstellen.stream().map(UserKostenstelle::getKostenstelle).collect(Collectors.toSet()), organisationseinheiten);
    }

    public AuthDto registerUser(RegisterDto registerDto) {
        String emailLowerCase = registerDto.email().toLowerCase();
        Optional<User> userWithEmail = userRepository.findByEmail(emailLowerCase);
        if (userWithEmail.isPresent()) throw new IllegalStateException();
        User user = new User();
        user.setEmail(emailLowerCase);
        user.setPasswordHash(passwordEncoder.encode(registerDto.password()));
        if (registerDto.kostenstellen() != null) {
            registerDto.kostenstellen().forEach(it -> {
                var kostenstelleCheck = datensatzRepository.findDatensatzByKostenstelle(it);
                if (kostenstelleCheck.isEmpty()) throw new IllegalStateException();
                UserKostenstelle userKostenstelle = new UserKostenstelle();
                userKostenstelle.setKostenstelle(it);
                userKostenstelle.setUser(user);
                user.getKostenstellen().add(userKostenstelle);
            });
        }
        userRepository.save(user);
        var organisationseinheiten = datenService.getOrganisationseinheitenForUser(user);
        return new AuthDto(jwtService.generateToken(user), registerDto.kostenstellen(), organisationseinheiten);
    }
}
