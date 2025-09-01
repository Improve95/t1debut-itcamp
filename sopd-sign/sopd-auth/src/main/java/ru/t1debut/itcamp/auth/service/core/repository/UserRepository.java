package ru.t1debut.itcamp.auth.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1debut.itcamp.auth.service.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
