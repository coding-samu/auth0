package com.okta.developer.auth0.controllers;

import com.okta.developer.auth0.entities.Group;
import com.okta.developer.auth0.services.GroupService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api")
class GroupController {

    private final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;

    @Autowired
    public GroupController(
            final GroupService groupService
    ) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public Collection<Group> groups(Principal principal) {
        LOGGER.info("[Controller] Request to get all groups");
        return groupService.groups(principal);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        LOGGER.info("[Controller] Request to get group: {}", id);
        return groupService.getGroup(id);
    }

    @PostMapping("/group")
    public ResponseEntity<Group> createGroup(
            @Valid @RequestBody Group group,
            @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        LOGGER.info("[Controller] Request to create group: {}", group);
        return groupService.createGroup(group, principal);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        LOGGER.info("[Controller] Request to update group: {}", group);
        return groupService.updateGroup(group);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        LOGGER.info("[Controller] Request to delete group: {}", id);
        return groupService.deleteGroup(id);
    }
}
