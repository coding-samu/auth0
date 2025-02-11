package com.okta.developer.auth0.repositories;

import com.okta.developer.auth0.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
