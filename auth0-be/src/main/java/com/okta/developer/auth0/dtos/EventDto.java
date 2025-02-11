package com.okta.developer.auth0.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@ToString
public class EventDto {
    private Long id;
    private Instant date;
    private String title;
    private String description;
    private Set<UserDto> attendees;
}
