package com.okta.developer.auth0.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> getUser(OAuth2User user);
    ResponseEntity<?> logout(HttpServletRequest request);
}
