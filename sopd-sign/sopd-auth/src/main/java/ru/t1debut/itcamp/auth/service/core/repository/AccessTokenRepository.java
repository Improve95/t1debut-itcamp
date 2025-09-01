package ru.t1debut.itcamp.auth.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1debut.itcamp.auth.service.model.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

    boolean existsAccessTokenByToken(String token);

    void deleteAllByUserId(int userId);

    void deleteByToken(String token);
}
