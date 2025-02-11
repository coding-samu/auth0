package com.okta.developer.auth0;

import com.okta.developer.auth0.entities.Event;
import com.okta.developer.auth0.entities.Group;
import com.okta.developer.auth0.repositories.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final GroupRepository repository;

    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Utah JUG", "Dallas JUG", "Tampa JUG", "Nashville JUG", "Detroit JUG")
                .forEach(name -> repository.save(new Group(name)));

        Group jug = repository.findByName("Tampa JUG");
        Event e = new Event(Instant.parse("2024-04-24T18:00:00.000Z"),
                "What the Heck is OAuth?",
                "Learn how and where OAuth can benefit your applications.");
        jug.setEvents(Collections.singleton(e));
        repository.save(jug);

        repository.findAll().forEach(System.out::println);
    }
}
