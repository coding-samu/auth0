package com.okta.developer.auth0.services.impl;

import com.okta.developer.auth0.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static java.util.Map.of;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ClientRegistration registration;

    public UserServiceImpl(
            final ClientRegistrationRepository registrations
    ) {
        this.registration = registrations.findByRegistrationId("okta");
    }

    @Override
    public ResponseEntity<?> getUser(OAuth2User user) {
        LOGGER.info("[Service] Request to get user: {}", user);
        if (user == null) {
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            return ResponseEntity.ok().body(user.getAttributes());
        }
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        LOGGER.info("[Service] Request to logout user");
        // send logout URL to client so they can initiate logout
        var issuerUri = registration.getProviderDetails().getIssuerUri();
        var originUrl = request.getHeader(HttpHeaders.ORIGIN);
        Object[] params = {issuerUri, registration.getClientId(), originUrl};
        // Yes! We @ Auth0 should have an end_session_endpoint in our OIDC metadata.
        // It's not included at the time of this writing, but will be coming soon!
        var logoutUrl = MessageFormat.format("{0}v2/logout?client_id={1}&returnTo={2}", params);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(of("logoutUrl", logoutUrl));
    }
}
