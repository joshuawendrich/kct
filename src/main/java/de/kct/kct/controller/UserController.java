package de.kct.kct.controller;

import de.kct.kct.dto.AuthDto;
import de.kct.kct.dto.LoginDto;
import de.kct.kct.dto.RegisterDto;
import de.kct.kct.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    ResponseEntity<AuthDto> loginUser(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.loginUser(loginDto), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    ResponseEntity<AuthDto> registerUser(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(userService.registerUser(registerDto), HttpStatus.CREATED);
    }
}
