package ru.improve.itcamp.auth.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.itcamp.auth.service.model.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

}
