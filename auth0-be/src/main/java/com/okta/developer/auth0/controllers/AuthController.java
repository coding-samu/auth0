package com.okta.developer.auth0.controllers;

import com.okta.developer.auth0.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(
            final UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User user) {
        LOGGER.info("[Controller] Request to get user: {}", user);
        return userService.getUser(user);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        LOGGER.info("[Controller] Request to logout user");
        return userService.logout(request);
    }
}
