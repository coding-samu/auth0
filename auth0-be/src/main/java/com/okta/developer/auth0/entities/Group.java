package com.okta.developer.auth0.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "user_group")
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> events;

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

}
