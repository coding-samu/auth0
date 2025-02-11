package com.okta.developer.auth0.services.impl;

import com.okta.developer.auth0.entities.Group;
import com.okta.developer.auth0.entities.User;
import com.okta.developer.auth0.repositories.GroupRepository;
import com.okta.developer.auth0.repositories.UserRepository;
import com.okta.developer.auth0.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(
            final GroupRepository groupRepository,
            final UserRepository userRepository
    ) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<Group> groups(Principal principal) {
        LOGGER.info("[Service] Request to get all groups");
        return groupRepository.findAllByUserId(principal.getName());
    }

    @Override
    public ResponseEntity<?> getGroup(Long id) {
        LOGGER.info("[Service] Request to get group: {}", id);
        Optional<Group> group = groupRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Group> createGroup(
            Group group,
            OAuth2User principal
    ) throws URISyntaxException {
        LOGGER.info("[Service] Request to create group: {}", group);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        // check to see if user already exists
        Optional<User> user = userRepository.findById(userId);
        group.setUser(user.orElse(new User(userId,
                details.get("name").toString(), details.get("email").toString())));

        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }

    @Override
    public ResponseEntity<Group> updateGroup(Group group) {
        LOGGER.info("[Service] Request to update group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Group> deleteGroup(Long id) {
        LOGGER.info("[Service] Request to delete group: {}", id);
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
