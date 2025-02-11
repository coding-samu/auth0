package com.okta.developer.auth0.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class GroupDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    private UserDto user;
    private Set<EventDto> events;
}
