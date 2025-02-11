package com.okta.developer.auth0.services;

import com.okta.developer.auth0.entities.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;

@Service
public interface GroupService {
    Collection<Group> groups(Principal principal);
    ResponseEntity<?> getGroup(Long id);
    ResponseEntity<Group> createGroup(Group group, OAuth2User principal) throws URISyntaxException;
    ResponseEntity<Group> updateGroup(Group group);
    ResponseEntity<Group> deleteGroup(Long id);
}
