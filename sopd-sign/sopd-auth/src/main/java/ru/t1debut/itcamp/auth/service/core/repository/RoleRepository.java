package ru.t1debut.itcamp.auth.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1debut.itcamp.auth.service.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
