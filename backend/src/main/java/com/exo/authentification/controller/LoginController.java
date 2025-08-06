package com.exo.authentification.controller;

import com.exo.authentification.config.UserAuthProvider;
import com.exo.authentification.dto.CredentialsDto;
import com.exo.authentification.dto.SignUpDto;
import com.exo.authentification.dto.UserDto;
import com.exo.authentification.mapper.UserMapper;
import com.exo.authentification.repository.UserRepository;
import com.exo.authentification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        return ResponseEntity.created(URI.create("/users" + user.getId())).body(user);


    }


}
