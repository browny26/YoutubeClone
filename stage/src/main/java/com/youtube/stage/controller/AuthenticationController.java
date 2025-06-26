package com.youtube.stage.controller;

import com.youtube.stage.config.CustomUserDetails;
import com.youtube.stage.dto.*;
import com.youtube.stage.model.User;
import com.youtube.stage.service.AuthenticationService;
import com.youtube.stage.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
//        User authenticatedUser = authenticationService.authenticate(loginUserDto);
//
//        System.out.println("Login effettuato da: " + authenticatedUser.getUsername());
//
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//
//        LoginResponseDto loginResponse = new LoginResponseDto()
//                .setToken(jwtToken)
//                .setExpiresIn(jwtService.getExpirationTime())
//                .setUsername(authenticatedUser.getUsername())
//                .setEmail(authenticatedUser.getEmail())
//                .setRoles(authenticatedUser.getRoles());
//
//        System.out.println("Token generato: " + jwtToken);
//
//        return ResponseEntity.ok(loginResponse);
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginRequest) {
        System.out.println(">>> Login endpoint called with email: " + loginRequest.getEmail());

        try {
            System.out.println("Authenticating user: " + loginRequest.getEmail());
            User user = authenticationService.authenticate(loginRequest);
            System.out.println("Authentication successful for user: " + user.getEmail());

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPasswordHash(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList())
            );

            String token = jwtService.generateToken(userDetails);

            LoginResponseDto response = new LoginResponseDto()
                    .setToken(token)
                    .setExpiresIn(jwtService.getExpirationTime())
                    .setUsername(user.getUsername())
                    .setEmail(user.getEmail())
                    .setRoles(user.getRoles());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    @GetMapping("/status")
//    public ResponseEntity<UserInfoDto> getCurrentUser(Authentication authentication) {
//        System.out.println("Authentication: " + authentication);
//        System.out.println("Authorities: " + authentication.getAuthorities());
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String username = userDetails.getUsername();
//        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
//
//        // Mappa UserDetails a DTO di risposta
//        UserInfoDto dto = new UserInfoDto();
//        dto.setUsername(username);
//        dto.setRoles(roles.stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toSet()));
//
//        return ResponseEntity.ok(dto);
//    }

//    @GetMapping("/status")
//    public ResponseEntity<?> getStatus(Authentication authentication) {
//        System.out.println("Auth in controller: " + authentication);
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//
//        // Restituisci qualcosa, ad esempio email o username:
//        return ResponseEntity.ok(Map.of(
//                "id", userDetails.getId(),
//                "email", userDetails.getUsername(),
//                "username", userDetails.getDisplayUsername(),  // o da userDetails.getUser() se hai getter
//                "roles", userDetails.getAuthorities(),
//                "followers", userDetails.getFollower(),
//                "avatar", userDetails.getAvatarUrl()
//        ));
//    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(Authentication authentication) {
        System.out.println("Auth in controller: " + authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Trasformiamo le authorities in lista di stringhe semplici
        List<String> roles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());

        UserStatusDTO dto = new UserStatusDTO(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getDisplayUsername(),
                roles,
                userDetails.getFollower(),  // assicurati che sia int o tipo serializzabile
                userDetails.getAvatarUrl()
        );

        return ResponseEntity.ok(dto);
    }

}