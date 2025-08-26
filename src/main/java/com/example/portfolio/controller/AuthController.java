package com.example.portfolio.controller;

import com.example.portfolio.domain.User;
import com.example.portfolio.domain.enums.RoleName;
import com.example.portfolio.dto.ApiResponse;
import com.example.portfolio.dto.AuthResponse;
import com.example.portfolio.dto.LoginRequest;
import com.example.portfolio.dto.RegisterRequest;
import com.example.portfolio.repository.UserRepository;
import com.example.portfolio.security.JwtService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void seedAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@example.com")
                    .fullName("Super Admin")
                    .roles(Set.of(RoleName.ADMIN))
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            log.info("Admin user created: {}", admin.getUsername());

        }
    }


    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.warn("Invalid login attempt for username: {}", req.getUsername());
            return ApiResponse.fail("Invalid username or password");
        }

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateAccessToken(
                user.getUsername(),
                user.getRoles().stream().map(Enum::name).toList()
        );
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roles(user.getRoles().stream().map(Enum::name).toList())
                .build();

        log.info("User {} logged in successfully with roles {}", user.getUsername(), user.getRoles());
        return ApiResponse.ok(response);
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestParam String refreshToken) {
        if (jwtService.isTokenExpired(refreshToken)) {
            return ApiResponse.fail("Refresh token expired, please login again");
        }

        String username = jwtService.getSubject(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(
                user.getUsername(),
                user.getRoles().stream().map(Enum::name).toList()
        );

        AuthResponse response = AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .roles(user.getRoles().stream().map(Enum::name).toList())
                .build();

        return ApiResponse.ok(response);
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            log.warn("Registration failed: username {} already taken", req.getUsername());
            return ApiResponse.fail("Username already taken");
        }
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .fullName(req.getFullName())
                .roles(Set.of(RoleName.USER))
                .enabled(true)
                .build();
        userRepository.save(user);
        log.info("New user registered: {}", user.getUsername());

        String accessToken = jwtService.generateAccessToken(
                user.getUsername(),
                user.getRoles().stream().map(Enum::name).toList()
        );
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roles(user.getRoles().stream().map(Enum::name).toList())
                .build();

        return ApiResponse.ok(response);
    }

}
