package com.halcyon.mention.service.auth;

import com.halcyon.mention.dto.auth.SignUpDto;
import com.halcyon.mention.model.Token;
import com.halcyon.mention.model.User;
import com.halcyon.mention.security.AuthRequest;
import com.halcyon.mention.security.AuthResponse;
import com.halcyon.mention.security.JwtAuthentication;
import com.halcyon.mention.service.token.TokenService;
import com.halcyon.mention.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthResponse signup(SignUpDto dto) {
        if (userService.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists.");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userService.create(user);

        String accessToken = jwtProvider.generateToken(user, false);
        String refreshToken = jwtProvider.generateToken(user, true);

        tokenService.create(new Token(refreshToken, user));

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(AuthRequest authRequest) {
        User user = userService.findByEmail(authRequest.getEmail());

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data.");
        }

        String accessToken = jwtProvider.generateToken(user, false);
        String refreshToken = jwtProvider.generateToken(user, true);

        tokenService.create(new Token(refreshToken, user));

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse getTokensByRefresh(String refreshToken, boolean isRefresh) {
        String subject = jwtProvider.extractRefreshClaims(refreshToken).getSubject();

        if (!jwtProvider.isValidRefreshToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token is not valid.");
        }

        User user = userService.findByEmail(subject);

        String accessToken = jwtProvider.generateToken(user, false);
        String newRefreshToken = null;

        if (isRefresh) {
            newRefreshToken = jwtProvider.generateToken(user, true);
            tokenService.create(new Token(newRefreshToken, user));
        }

        return new AuthResponse(accessToken, newRefreshToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}