package com.api.mvc.controller;

import com.api.mvc.config.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public static class AuthRequest {
        public String username;
        public String password;
    }

    public static class AuthResponse {
        public String token;
        public AuthResponse(String token) { this.token = token; }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username, request.password)
        );
        if (auth.isAuthenticated()) {
            String token = jwtTokenUtil.generateToken(request.username);
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(401).build();
    }
}
