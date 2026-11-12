package com.example.demo.controller;

import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserDetailsRepository;
import com.example.demo.service.UserService;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        try {
            // Authentifier l'utilisateur
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(),
                            authRequest.password())
            );
            // Créer JWT Token
            String jwt = jwtUtil.generateToken(authRequest.username());
            Token token = new Token();
            token.setUser(userDetailsRepository.findByUsername(authRequest.username()));
            token.setToken(jwt);
            token.setExpired(false);
            token.setRevoked(false);
            Users user = userDetailsRepository.findByUsername(authRequest.username());
            userService.revokeUserToken(user);
            tokenRepository.save(token);

            return jwt;

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
