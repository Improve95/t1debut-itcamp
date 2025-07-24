package ru.improve.itcamp.auth.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.itcamp.auth.service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
