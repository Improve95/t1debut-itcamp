package ru.improve.itcamp.auth.service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.improve.itcamp.auth.service.model.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

    /* очень хотелось оптимизировать запросы, хотя стоило использовать jdbc в таком случае */

    @Modifying
    @Query(nativeQuery = true, value = "insert into white_list_access_tokens(token, user_id) values (:token, :userId)")
    void saveToken(String token, int userId);

    @Query(
            nativeQuery = true,
            value = """
                select count(wt.token) > 0 
                    from (select wl.token from white_list_access_tokens wl where wl.user_id = :userId) as wt 
                    where wt.token = :token
                """)
    boolean existsAccessTokenByTokenAndUserId(String token, int userId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from white_list_access_tokens wl where wl.user_id = :userId")
    void deleteAllByUserId(int userId);
}
